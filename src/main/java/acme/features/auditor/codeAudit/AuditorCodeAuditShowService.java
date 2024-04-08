
package acme.features.auditor.codeAudit;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.codeaudits.AuditRecord;
import acme.entities.codeaudits.CodeAudit;
import acme.entities.codeaudits.Mark;
import acme.roles.Auditor;

@Service
public class AuditorCodeAuditShowService extends AbstractService<Auditor, CodeAudit> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private AuditorCodeAuditRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		CodeAudit object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findOneCodeAuditById(id);

		super.getBuffer().addData(object);
	}

	@Override
	public void unbind(final CodeAudit object) {
		assert object != null;

		Dataset dataset;
		Collection<AuditRecord> auditRecords;

		auditRecords = this.repository.findAllAuditRecordsByCodeAuditId(object.getId());

		// Map to store frequency of each mode
		Map<Mark, Integer> modeMap = new HashMap<>();

		// Iterate through auditRecords and count occurrences of each mode
		for (AuditRecord record : auditRecords) {
			Mark mode = record.getMark(); // Assuming there's a getter method for the mode
			modeMap.put(mode, modeMap.getOrDefault(mode, 0) + 1);
		}

		// Find the mode with the highest frequency
		Mark mode = null;
		int maxFrequency = 0;
		for (Map.Entry<Mark, Integer> entry : modeMap.entrySet()) {
			int frequency = entry.getValue();
			if (frequency > maxFrequency) {
				maxFrequency = frequency;
				mode = entry.getKey();
			}
		}

		dataset = super.unbind(object, "code", "executionDate", "type", "correctiveActions", "link");
		dataset.put("Mark", mode);

		super.getResponse().addData(dataset);
	}

}
