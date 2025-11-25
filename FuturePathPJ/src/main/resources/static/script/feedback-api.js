createLoadingScreenBody();

document.addEventListener("DOMContentLoaded", () => {
    
    const prevBtn = document.querySelector('.prev-btn');
    const nextBtn = document.querySelector('.next-btn');
    const inputPage = document.querySelector('.input-page');
	const pageBtns = document.querySelectorAll('.btn-page');
	const search = document.querySelector('.search');


    // Load first page
    loadUsers(0);

    if (nextBtn) {
        nextBtn.addEventListener('click', () => {
			createLoadingScreenBody();
			const searchValue = search.value;
            loadUsers(currentPage, searchValue); 
        });
    }

    if (prevBtn) {
        prevBtn.addEventListener('click', () => {
			createLoadingScreenBody();
			const searchValue = search.value;
            loadUsers(currentPage - 2, searchValue); 
        });
    }

	if(pageBtns){
		pageBtns.forEach(btn => btn.addEventListener('click', function(){
			createLoadingScreenBody();
			const searchValue = search.value;
            loadUsers(Number(this.textContent.trim()) - 1, searchValue); 
		}));
	}

    if (inputPage) {
        inputPage.addEventListener('change', () => {
			createLoadingScreenBody();
			const searchValue = search.value;
            let newPage = Number(inputPage.value);
            if (newPage < 1) newPage = 1;
            inputPage.value = newPage;
            loadUsers(newPage - 1, searchValue);
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
	            loadUsers(currentPage, searchValue);
	        }, delay);
	    });
	}

});


async function loadUsers(page = 0,
	search = ""
) {
    try {

		const params = new URLSearchParams({ page, search });
				
		const url = `/api/admin/feedback/retrieve?${params.toString()}`;
				
        const response = await fetch(url);
        const data = await response.json();
		
        updatePagination(data.pagination);

        const tableBody = document.getElementById("table-body");
		tableBody.innerHTML = '';

        const fragment = document.createDocumentFragment();

        data.feedbacks.forEach(feedback => {
            const row = document.createElement("tr");
            row.classList.add("table-row");
			row.setAttribute('data-id', feedback.resultIdPk);
		
			row.innerHTML = `
			    <td><i class="fa-solid fa-hashtag">${feedback.resultIdPk}</td>

			    <td>${feedback.firstName} ${feedback.lastName} (@${feedback.username})</td>

			    <td class="rating-cell rating-${feedback.ratings}">
			        <span class="rating-badge">
			            ${feedback.ratings} <i class="fa-solid fa-star"></i>
			        </span>
			    </td>

			    <td>${feedback.feedback}</td>
			`;
			
            fragment.appendChild(row);
        });

		
		
        tableBody.appendChild(fragment);
		
		addLoadingListener();
		
		removeLoadingScreenBody();

    } catch (error) {
        console.error("Error fetching feedbacks:", error);
    }
}