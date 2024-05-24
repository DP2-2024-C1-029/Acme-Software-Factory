
package acme.features.sponsor.sponsorship;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.client.views.SelectChoices;
import acme.entities.projects.Project;
import acme.entities.sponsorships.SponsorType;
import acme.entities.sponsorships.Sponsorship;
import acme.roles.Sponsor;

@Service
public class SponsorSponsorshipShowService extends AbstractService<Sponsor, Sponsorship> {

	// Internal state ---------------------------------------------------------

	@Autowired
	public SponsorSponsorshipRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		boolean status;
		int sponsorshipId;
		Sponsorship sponsorship;

		sponsorshipId = super.getRequest().getData("id", int.class);
		sponsorship = this.repository.findOneSponsorshipById(sponsorshipId);
		status = sponsorship != null && super.getRequest().getPrincipal().hasRole(sponsorship.getSponsor());

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Sponsorship object;
		int sponsorId;

		sponsorId = super.getRequest().getData("id", int.class);
		object = this.repository.findOneSponsorshipById(sponsorId);

		super.getBuffer().addData(object);
	}

	@Override
	public void unbind(final Sponsorship object) {
		assert object != null;

		int sponsorId;
		Collection<Project> projects;
		SelectChoices choicesType;
		SelectChoices choicesProject;
		Dataset dataset;

		sponsorId = super.getRequest().getPrincipal().getActiveRoleId();
		choicesType = SelectChoices.from(SponsorType.class, object.getType());
		projects = this.repository.findManyProjects();
		choicesProject = SelectChoices.from(projects, "title", object.getProject());

		dataset = super.unbind(object, "code", "moment", "initialExecutionPeriod", "endingExecutionPeriod", "amount", "type", "email", "link", "isPublished");
		dataset.put("type", choicesType.getSelected().getKey());
		dataset.put("types", choicesType);
		dataset.put("project", choicesProject.getSelected().getKey());
		dataset.put("projects", choicesProject);
		dataset.put("showInvoices", sponsorId == object.getSponsor().getId());

		super.getResponse().addData(dataset);
	}

}
