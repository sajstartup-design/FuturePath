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
				
		const url = `/api/admin/users/retrieve?${params.toString()}`;
				
        const response = await fetch(url);
        const data = await response.json();
		
        updatePagination(data.pagination);

        const tableBody = document.getElementById("table-body");
		tableBody.innerHTML = '';

        const fragment = document.createDocumentFragment();

        data.users.forEach(user => {
            const row = document.createElement("tr");
            row.classList.add("table-row");
			row.setAttribute('data-id', user.idPk);
			
			const date = new Date(user.createdAt);
			const options = { year: 'numeric', month: 'short', day: 'numeric', hour: '2-digit', minute:'2-digit' };
			const formattedDate = date.toLocaleDateString('en-US', options);

            row.innerHTML = `
                <td>${user.idPk}</td>
				<td>${user.username}</td>
				<td>${user.firstName}</td>
				<td>${user.lastName}</td>
				<td>${user.email}</td>
				<td>${user.phone}</td>
				<td>${user.gender}</td>
				<td>${formattedDate}</td>
				<td><span class="status-label ${user.isActive ? 'active' : 'inactive'}">${user.isActive ? 'ACTIVE' : 'INACTIVE'}</span></td>
				<td class="actions-cell">
					<a href="/admin/user/details?idPk=${user.idPk}" class="btn btn-icon view transitioning"><i class="fa-solid fa-eye"></i></a>
		            <a href="/admin/user/edit?idPk=${user.idPk}" class="btn btn-icon edit transitioning"><i class="fa-solid fa-pen-to-square"></i></a>
					<button 
					    data-bs-toggle="modal" 
					    data-bs-target="#deleteModal" 
					    class="btn btn-icon delete"
					    data-name="${user.firstName} ${user.lastName}"
					    data-id="${user.idPk}"
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
        console.error("Error fetching users:", error);
    }
}