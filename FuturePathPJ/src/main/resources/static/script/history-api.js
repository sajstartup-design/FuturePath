let recentAssessments = []; // Store recent activities globally
let recentPopulated = false; // Ensure we populate only once

createLoadingScreenBody();

document.addEventListener("DOMContentLoaded", () => {
    
    const prevBtn = document.querySelector('.prev-btn');
    const nextBtn = document.querySelector('.next-btn');
    const inputPage = document.querySelector('.input-page');
	const pageBtns = document.querySelectorAll('.btn-page');
	const search = document.querySelector('.search');

    // Load first page
    loadHistory(0);

    if (nextBtn) {
        nextBtn.addEventListener('click', () => {
			createLoadingScreenBody();
			const searchValue = search.value;
            loadHistory(currentPage, searchValue); 
        });
    }

    if (prevBtn) {
        prevBtn.addEventListener('click', () => {
			createLoadingScreenBody();
			const searchValue = search.value;
            loadHistory(currentPage - 2, searchValue); 
        });
    }

	if(pageBtns){
		pageBtns.forEach(btn => btn.addEventListener('click', function(){
			createLoadingScreenBody();
			const searchValue = search.value;
            loadHistory(Number(this.textContent.trim()) - 1, searchValue); 
		}));
	}

    if (inputPage) {
        inputPage.addEventListener('change', () => {
			createLoadingScreenBody();
			const searchValue = search.value;
            let newPage = Number(inputPage.value);
            if (newPage < 1) newPage = 1;
            inputPage.value = newPage;
            loadHistory(newPage - 1, searchValue);
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
	            loadHistory(currentPage, searchValue);
	        }, delay);
	    });
	}

});

async function loadHistory(page = 0, search = "") {
    try {
        const params = new URLSearchParams({ page, search });
        const url = `/api/admin/history/retrieve?${params.toString()}`;

        const response = await fetch(url);
        const data = await response.json();

        updatePagination(data.pagination);

        // Populate table
        const tableBody = document.getElementById("table-body");
        if (tableBody) {
            tableBody.innerHTML = '';
            const fragment = document.createDocumentFragment();

            data.assessments.forEach(assessment => {
                const row = document.createElement("tr");
                row.classList.add("table-row");
                row.setAttribute('data-id', assessment.resultIdPk);
				
				const date = new Date(assessment.dateTaken);
	            const options = { year: 'numeric', month: 'short', day: 'numeric', hour: '2-digit', minute:'2-digit' };
	            const formattedDate = date.toLocaleDateString('en-US', options);

                row.innerHTML = `
                    <td>#${assessment.resultIdPk}</td>
                    <td>${assessment.fullName}</td>
					<td>${assessment.category}</td>
                    <td>${assessment.totalCorrect}</td>
                    <td>${assessment.totalIncorrect}</td>
                    <td>${assessment.totalQuestion}</td>
                    <td>${assessment.score}%</td>
                    <td>${formattedDate}</td>
                    <td class="actions-cell">
                        <a href="/assessment/result/view?resultIdPk=${assessment.resultIdPk}" class="btn btn-icon view">
                            <i class="fa-solid fa-eye"></i>
                        </a>
                    </td>
                `;
                fragment.appendChild(row);
            });

            tableBody.appendChild(fragment);
        }

        // Populate recent activities only once
		// Populate recent activities only once
		if (!recentPopulated) {
		    recentAssessments = data.assessments.slice(0, 5);
		    const recentContainer = document.getElementById("recent-activities");
		    if (recentContainer) {
		        recentContainer.innerHTML = '';
		        const recentFragment = document.createDocumentFragment();

		        recentAssessments.forEach(assessment => {
		            const div = document.createElement("div");
		            div.classList.add("activity");

		            // Format date nicely
		            const date = new Date(assessment.dateTaken);
		            const options = { year: 'numeric', month: 'short', day: 'numeric', hour: '2-digit', minute:'2-digit' };
		            const formattedDate = date.toLocaleDateString('en-US', options);

		            div.innerHTML = `
		                <span>
		                    <strong>${assessment.fullName}</strong> has taken a 
		                    <strong>${assessment.category}</strong> assessment and scored 
		                    <strong>${assessment.score}%</strong> on ${formattedDate}.
		                </span>
		            `;
		            recentFragment.appendChild(div);
		        });

		        recentContainer.appendChild(recentFragment);
		        recentPopulated = true; // Mark as populated
		    }
		}


        removeLoadingScreenBody();

    } catch (error) {
        console.error("Error fetching assessments:", error);
    }
}
