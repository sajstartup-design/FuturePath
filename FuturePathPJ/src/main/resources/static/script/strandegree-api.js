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

            row.innerHTML = `
                <td>${strandegree.idPk}</td>
				<td>${strandegree.name}</td>
				<td>${strandegree.code}</td>
				<td>${strandegree.category}</td>
				<td>${strandegree.details}</td>
				<td>${strandegree.createdAt}</td>
				<td><span class="status-label ${strandegree.isActive ? 'active' : 'inactive'}">${strandegree.isActive ? 'ACTIVE' : 'INACTIVE'}</span></td>
				<td class="actions-cell">
		            <button class="btn btn-icon edit"><i class="fa-solid fa-pen-to-square"></i></button>
		            <button class="btn btn-icon delete"><i class="fa-solid fa-trash"></i></button>
	            </td>
            `;
			
			/*row.querySelector('.edit-btn').addEventListener('click', function(){
				const form = document.querySelector('#editForm');
				
				form.querySelector('#hiddenEncryptedId').value = this.getAttribute('data-id');
				
				form.submit();
			});
			
			row.addEventListener('click', function(e) {
			   
			    if (e.target.closest('button') || e.target.closest('a')) {
			        return; 
			    }
				
				const encryptedId = this.getAttribute('data-id');

			    window.location.href="/admin/user/details?encryptedId=" + encryptedId;
			});
*/

            fragment.appendChild(row);
        });

        tableBody.appendChild(fragment);
		
		removeLoadingScreenBody();

    } catch (error) {
        console.error("Error fetching strandegree:", error);
    }
}