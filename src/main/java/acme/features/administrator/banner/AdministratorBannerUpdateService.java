
package acme.features.administrator.banner;

import java.time.temporal.ChronoUnit;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Administrator;
import acme.client.data.models.Dataset;
import acme.client.helpers.MomentHelper;
import acme.client.services.AbstractService;
import acme.entities.banners.Banner;

@Service
public class AdministratorBannerUpdateService extends AbstractService<Administrator, Banner> {

	// Internal state ---------------------------------------------------------

	@Autowired
	public AdministratorBannerRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		boolean status;
		int bannerId;
		Banner banner;

		bannerId = super.getRequest().getData("id", int.class);
		banner = this.repository.findOneBannerById(bannerId);
		status = banner != null && super.getRequest().getPrincipal().hasRole(Administrator.class);

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Banner object;
		int bannerId;

		bannerId = super.getRequest().getData("id", int.class);
		object = this.repository.findOneBannerById(bannerId);

		super.getBuffer().addData(object);
	}

	@Override
	public void bind(final Banner object) {
		assert object != null;

		super.bind(object, "displayStartMoment", "displayEndMoment", "picture", "slogan", "link");
	}

	@Override
	public void validate(final Banner object) {
		assert object != null;

		if (!super.getBuffer().getErrors().hasErrors("displayStartMoment"))
			super.state(MomentHelper.isAfter(object.getDisplayStartMoment(), object.getInstantiationMoment()), "displayStartMoment", "administrator.banner.form.error.not-after");

		if (!super.getBuffer().getErrors().hasErrors("displayEndMoment") && !super.getBuffer().getErrors().hasErrors("displayStartMoment")) {
			Date minimumDeadline;

			minimumDeadline = MomentHelper.deltaFromMoment(object.getDisplayStartMoment(), 1, ChronoUnit.WEEKS);
			super.state(MomentHelper.isAfter(object.getDisplayEndMoment(), minimumDeadline), "displayEndMoment", "administrator.banner.form.error.too-close");
		}
	}

	@Override
	public void perform(final Banner object) {
		assert object != null;

		this.repository.save(object);
	}

	@Override
	public void unbind(final Banner object) {
		assert object != null;

		Dataset dataset;

		dataset = super.unbind(object, "instantiationMoment", "displayStartMoment", "displayEndMoment", "picture", "slogan", "link");

		super.getResponse().addData(dataset);
	}
}
