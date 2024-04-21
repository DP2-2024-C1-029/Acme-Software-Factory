
package acme.features.administrator.banner;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.client.controllers.AbstractController;
import acme.client.data.accounts.Administrator;
import acme.entities.banners.Banner;

@Controller
public class AdministratorBannerController extends AbstractController<Administrator, Banner> {

	// Internal state ---------------------------------------------------------

	@Autowired
	public AdministratorBannerShowService	showService;

	@Autowired
	public AdministratorBannerListService	listService;

	@Autowired
	public AdministratorBannerCreateService	createService;

	@Autowired
	public AdministratorBannerUpdateService	updateService;

	@Autowired
	public AdministratorBannerDeleteService	deleteService;

	// AbstractController interface ----------------------------------------------


	@PostConstruct
	public void initialize() {
		this.addBasicCommand("show", this.showService);
		this.addBasicCommand("list", this.listService);
		this.addBasicCommand("create", this.createService);
		this.addBasicCommand("update", this.updateService);
		this.addBasicCommand("delete", this.deleteService);
	}
}
