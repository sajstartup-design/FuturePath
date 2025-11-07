function updateModalButtons() {

    // OPEN MODAL + FILL DATA
    document.addEventListener("click", function (e) {
        const button = e.target.closest("[data-bs-toggle='modal']");
        if (!button) return;

        const target = button.getAttribute("data-bs-target");
        const modal = document.querySelector(target);

        if (modal) {
            modal.classList.remove("hidden");
			
			if(modal.id === 'deleteModal'){
				
				const name = modal.querySelector("#name");
				
				if(name){
					name.textContent = button.getAttribute("data-name");
				}
				
				const idPk = document.getElementById("idPk");
				
				if(idPk){
					const idValue = button.getAttribute("data-id");
					idPk.value = idValue;
				}
				
				const category = document.getElementById('category');
				
				if(category){
					const categoryValue = button.getAttribute("data-category");
					category.value = categoryValue;
				}


				document.getElementById("confirmDeleteBtn")
				    .setAttribute("data-id", button.getAttribute("data-id"));

			}
        }
    });

    // CLOSE MODAL (Cancel button)
    document.addEventListener("click", function (e) {
        if (e.target.matches("[data-close='modal']")) {
            e.target.closest(".modal-overlay").classList.add("hidden");
        }
    });

    // CLOSE MODAL (clicking outside)
    document.addEventListener("click", function (e) {
        if (e.target.classList.contains("modal-overlay")) {
            e.target.classList.add("hidden");
        }
    });
}
