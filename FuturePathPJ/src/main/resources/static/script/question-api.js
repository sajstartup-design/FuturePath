createLoadingScreenBody();

document.addEventListener("DOMContentLoaded", () => {
    
    const prevBtn = document.querySelector('.prev-btn');
    const nextBtn = document.querySelector('.next-btn');
    const inputPage = document.querySelector('.input-page');
	const pageBtns = document.querySelectorAll('.btn-page');
	const search = document.querySelector('.search');


    // Load first page
    loadQuestions(0);

    if (nextBtn) {
        nextBtn.addEventListener('click', () => {
			createLoadingScreenBody();
			const searchValue = search.value;
            loadQuestions(currentPage, searchValue); 
        });
    }

    if (prevBtn) {
        prevBtn.addEventListener('click', () => {
			createLoadingScreenBody();
			const searchValue = search.value;
            loadQuestions(currentPage - 2, searchValue); 
        });
    }
	console.log(pageBtns);
	if(pageBtns){
		pageBtns.forEach(btn => btn.addEventListener('click', function(){
			createLoadingScreenBody();
			const searchValue = search.value;
            loadQuestions(Number(this.textContent.trim()) - 1, searchValue); 
		}));
	}

    if (inputPage) {
        inputPage.addEventListener('change', () => {
			createLoadingScreenBody();
			const searchValue = search.value;
            let newPage = Number(inputPage.value);
            if (newPage < 1) newPage = 1;
            inputPage.value = newPage;
            loadQuestions(newPage - 1, searchValue);
        });
    }
	
	if (search) {
	    let typingTimer; 
	    const delay = 500; 

	    search.addEventListener('input', function () {
	        clearTimeout(typingTimer); 

	        const currentPage = 0;
	        const searchValue = this.value;

	        typingTimer = setTimeout(() => {
				createLoadingScreenBody();
	            loadQuestions(currentPage, searchValue);
	        }, delay);
	    });
	}

});


async function loadQuestions(page = 0,
	search = ""
) {
    try {
		console.log(search);
		const params = new URLSearchParams({ page, search });
				
		const url = `/api/admin/questions/retrieve?${params.toString()}`;
				
        const response = await fetch(url);
        const data = await response.json();
		
		console.log(data);

        updatePagination(data.pagination);

        const tableBody = document.getElementById("table-body");
		tableBody.innerHTML = '';

        const fragment = document.createDocumentFragment();

        data.questions.forEach(question => {
            const row = document.createElement("tr");
            row.classList.add("table-row");
			row.setAttribute('data-id', question.idPk);

            row.innerHTML = `
                <td>${question.idPk}</td>
				<td>${question.createdAt}</td>
				<td><span class="status-label ${question.isActive ? 'active' : 'inactive'}">${question.isActive ? 'ACTIVE' : 'INACTIVE'}</span></td>
				<td class="actions-cell">
		            <button class="btn btn-icon edit"><i class="fa-solid fa-pen-to-square"></i></button>
		            <button class="btn btn-icon delete"><i class="fa-solid fa-trash"></i></button>
	            </td>
            `;
			
            fragment.appendChild(row);
        });

        tableBody.appendChild(fragment);
		
		removeLoadingScreenBody();

    } catch (error) {
        console.error("Error fetching questions:", error);
    }
}