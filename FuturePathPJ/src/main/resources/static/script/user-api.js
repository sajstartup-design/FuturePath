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
	console.log(pageBtns);
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
		console.log(search);
		const params = new URLSearchParams({ page, search });
				
		const url = `/api/admin/users/retrieve?${params.toString()}`;
				
        const response = await fetch(url);
        const data = await response.json();
		
		console.log(data);

        updatePagination(data.pagination);

        const tableBody = document.getElementById("table-body");
		tableBody.innerHTML = '';

        const fragment = document.createDocumentFragment();

        data.users.forEach(user => {
            const row = document.createElement("tr");
            row.classList.add("table-row");
			row.setAttribute('data-id', user.idPk);

            row.innerHTML = `
                <td>${user.idPk}</td>
				<td>${user.firstName}</td>
				<td>${user.lastName}</td>
				<td>${user.email}</td>
				<td>${user.phone}</td>
				<td>${user.gender}</td>
				<td>${user.createdAt}</td>
				<td><span class="status-label ${user.isActive ? 'active' : 'inactive'}">${user.isActive ? 'ACTIVE' : 'INACTIVE'}</span></td>
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
        console.error("Error fetching users:", error);
    }
}