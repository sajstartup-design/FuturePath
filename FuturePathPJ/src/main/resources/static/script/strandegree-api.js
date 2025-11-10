createLoadingScreenBody();

document.addEventListener("DOMContentLoaded", () => {
    
    const prevBtn = document.querySelector('.prev-btn');
    const nextBtn = document.querySelector('.next-btn');
    const inputPage = document.querySelector('.input-page');
	const pageBtns = document.querySelectorAll('.btn-page');
	const search = document.querySelector('.search');


    // Load first page
    loadStrandegrees(0);

    if (nextBtn) {
        nextBtn.addEventListener('click', () => {
			createLoadingScreenBody();
			const searchValue = search.value;
            loadStrandegrees(currentPage, searchValue); 
        });
    }

    if (prevBtn) {
        prevBtn.addEventListener('click', () => {
			createLoadingScreenBody();
			const searchValue = search.value;
            loadStrandegrees(currentPage - 2, searchValue); 
        });
    }
	console.log(pageBtns);
	if(pageBtns){
		pageBtns.forEach(btn => btn.addEventListener('click', function(){
			createLoadingScreenBody();
			const searchValue = search.value;
            loadStrandegrees(Number(this.textContent.trim()) - 1, searchValue); 
		}));
	}

    if (inputPage) {
        inputPage.addEventListener('change', () => {
			createLoadingScreenBody();
			const searchValue = search.value;
            let newPage = Number(inputPage.value);
            if (newPage < 1) newPage = 1;
            inputPage.value = newPage;
            loadStrandegrees(newPage - 1, searchValue);
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
	            loadStrandegrees(currentPage, searchValue);
	        }, delay);
	    });
	}

});


async function loadStrandegrees(page = 0,
	search = ""
) {
    try {
		console.log(search);
		const params = new URLSearchParams({ page, search });
				
		const url = `/api/admin/strandegrees/retrieve?${params.toString()}`;
				
        const response = await fetch(url);
        const data = await response.json();
		
		console.log(data);

        updatePagination(data.pagination);

        const tableBody = document.getElementById("table-body");
		tableBody.innerHTML = '';

        const fragment = document.createDocumentFragment();

        data.strandegrees.forEach(strandegree => {
            const row = document.createElement("tr");
            row.classList.add("table-row");
			row.setAttribute('data-id', strandegree.idPk);
			
			const date = new Date(strandegree.createdAt);
            const options = { year: 'numeric', month: 'short', day: 'numeric', hour: '2-digit', minute:'2-digit' };
            const formattedDate = date.toLocaleDateString('en-US', options);

            row.innerHTML = `
                <td>${strandegree.idPk}</td>
				<td>${strandegree.name}</td>
				<td>${strandegree.code}</td>
				<td>${strandegree.category}</td>
				<td>${strandegree.details}</td>
				<td>${formattedDate}</td>
				<td><span class="status-label ${strandegree.isActive ? 'active' : 'inactive'}">${strandegree.isActive ? 'ACTIVE' : 'INACTIVE'}</span></td>
				<td class="actions-cell">
					<a href="/admin/strandegrees/details?idPk=${strandegree.idPk}" class="btn btn-icon view transitioning"><i class="fa-solid fa-eye"></i></a>
		            <a href="/admin/strandegrees/edit?idPk=${strandegree.idPk}" class="btn btn-icon edit transitioning"><i class="fa-solid fa-pen-to-square"></i></a>
					<button 
					    data-bs-toggle="modal" 
					    data-bs-target="#deleteModal" 
					    class="btn btn-icon delete"
					    data-name="${strandegree.name}"
					    data-id="${strandegree.idPk}"
						data-category="${strandegree.category}"
					>
					    <i class="fa-solid fa-trash"></i>
					</button>
	            </td>
            `;
	
            fragment.appendChild(row);
        });

        tableBody.appendChild(fragment);
		
		updateModalButtons();

		addLoadingListener();
		
		removeLoadingScreenBody();

    } catch (error) {
        console.error("Error fetching strandegree:", error);
    }
}