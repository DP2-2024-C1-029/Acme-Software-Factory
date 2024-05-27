
package acme.features.sponsor.sponsorship;

import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.datatypes.Money;
import acme.client.data.models.Dataset;
import acme.client.helpers.MomentHelper;
import acme.client.services.AbstractService;
import acme.client.views.SelectChoices;
import acme.entities.projects.Project;
import acme.entities.sponsorships.Invoice;
import acme.entities.sponsorships.SponsorType;
import acme.entities.sponsorships.Sponsorship;
import acme.roles.Sponsor;

@Service
public class SponsorSponsorshipPublishService extends AbstractService<Sponsor, Sponsorship> {

	// Internal state ---------------------------------------------------------

	@Autowired
	public SponsorSponsorshipRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		boolean status;
		int masterId;
		Sponsorship sponsorship;
		Sponsor sponsor;

		masterId = super.getRequest().getData("id", int.class);
		sponsorship = this.repository.findOneSponsorshipById(masterId);
		sponsor = sponsorship == null ? null : sponsorship.getSponsor();

		status = sponsorship != null && super.getRequest().getPrincipal().hasRole(sponsor) && !sponsorship.isPublished();

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Sponsorship sponsorship;
		int id;

		id = super.getRequest().getData("id", int.class);
		sponsorship = this.repository.findOneSponsorshipById(id);

		super.getBuffer().addData(sponsorship);
	}

	@Override
	public void bind(final Sponsorship object) {
		assert object != null;

		int projectId;
		Project project;

		projectId = super.getRequest().getData("project", int.class);
		project = this.repository.findOneProjectById(projectId);

		super.bind(object, "code", "initialExecutionPeriod", "endingExecutionPeriod", "amount", "type", "email", "link");
		object.setProject(project);
	}

	@Override
	public void validate(final Sponsorship object) {
		assert object != null;

		if (!super.getBuffer().getErrors().hasErrors("code")) {
			Sponsorship existing;

			existing = this.repository.findOneSponsorshipByCode(object.getCode());
			super.state(existing == null || existing.equals(object), "code", "sponsor.sponsorship.form.error.duplicated");
		}

		if (!super.getBuffer().getErrors().hasErrors("project")) {
			Collection<Project> myProjects;

			myProjects = this.repository.findManyProjects();

			super.state(myProjects.contains(object.getProject()), "project", "sponsor.sponsorship.form.error.not-exists");
		}

		if (!super.getBuffer().getErrors().hasErrors("endingExecutionPeriod") && !super.getBuffer().getErrors().hasErrors("initialExecutionPeriod")) {
			Date minimumDeadline;

			minimumDeadline = MomentHelper.deltaFromMoment(object.getInitialExecutionPeriod(), 7, ChronoUnit.DAYS);
			super.state(MomentHelper.isAfter(object.getEndingExecutionPeriod(), minimumDeadline), "endingExecutionPeriod", "sponsor.sponsorship.form.error.too-close");
		}

		if (!super.getBuffer().getErrors().hasErrors("initialExecutionPeriod"))
			super.state(MomentHelper.isAfter(object.getInitialExecutionPeriod(), object.getMoment()), "initialExecutionPeriod", "sponsor.sponsorship.form.error.not-after");

		if (!super.getBuffer().getErrors().hasErrors("amount")) {
			String[] acceptedCurrencies = this.repository.findAcceptedCurrencies().split(";");
			super.state(Stream.of(acceptedCurrencies).anyMatch(c -> c.equals(object.getAmount().getCurrency())), //
				"amount", "sponsor.sponsorship.form.error.currency-not-valid");

			super.state(object.getAmount().getAmount() > 0, "amount", "sponsor.sponsorship.form.error.negative-amount");
		}

		if (!super.getBuffer().getErrors().hasErrors("amount")) {
			Double sumTotalAmountsInvoices;

			sumTotalAmountsInvoices = this.repository.findManyInvoicesBySponsorshipId(object.getId()).stream().map(Invoice::totalAmount).mapToDouble(Money::getAmount).sum();
			sumTotalAmountsInvoices = Math.round(sumTotalAmountsInvoices * 100) / 100.0;
			super.state(sumTotalAmountsInvoices.equals(object.getAmount().getAmount()), "*", "sponsor.sponsorship.form.error.not-total-amount-invoices");
		}

		{
			boolean allInvoicesPublished;

			allInvoicesPublished = this.repository.findManyInvoicesBySponsorshipId(object.getId()).stream().allMatch(Invoice::isPublished);
			super.state(allInvoicesPublished, "*", "sponsor.sponsorship.form.error.not-all-invoices-published");
		}
	}

	@Override
	public void perform(final Sponsorship object) {
		assert object != null;

		object.setPublished(true);
		this.repository.save(object);
	}

	@Override
	public void unbind(final Sponsorship object) {
		assert object != null;

		Collection<Project> projects;
		SelectChoices choicesProject;
		SelectChoices choicesType;
		Dataset dataset;

		choicesType = SelectChoices.from(SponsorType.class, object.getType());
		projects = this.repository.findManyProjects();
		choicesProject = SelectChoices.from(projects, "title", object.getProject());

		dataset = super.unbind(object, "code", "moment", "initialExecutionPeriod", "endingExecutionPeriod", "amount", "type", "email", "link", "isPublished");
		dataset.put("type", choicesType.getSelected().getKey());
		dataset.put("types", choicesType);
		dataset.put("project", choicesProject.getSelected().getKey());
		dataset.put("projects", choicesProject);

		super.getResponse().addData(dataset);
	}
}
