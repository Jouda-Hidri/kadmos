package saving.saving.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import saving.saving.api.dto.TransactionRequest;
import saving.saving.api.dto.TransactionResponse;
import saving.saving.service.SavingService;

@RestController
public class SavingController {
	private final SavingService savingService;

	public SavingController(final SavingService savingService) {
		this.savingService = savingService;
	}

	@GetMapping
	public String index() {
		return "Hello World";
	}

	@PutMapping
	public ResponseEntity<TransactionResponse> update(@RequestBody final TransactionRequest request) {
		final var response = savingService.update(request.getAmount());
		return ResponseEntity.ok(response);
	}
}