let allStrandegrees = []; // store all items globally

createLoadingScreenBody();
loadStrandegrees();

async function loadStrandegrees() {
  try {
    const url = `/api/strandegrees/retrieve`;
    const response = await fetch(url);
    const data = await response.json();

    console.log(data);
    allStrandegrees = data.strandegrees || [];

    renderStrandegrees(allStrandegrees);
    removeLoadingScreenBody();
  } catch (error) {
    console.error("Error fetching strandegree:", error);
  }
}

function renderStrandegrees(list) {
  const container = document.querySelector(".strand-degree-list");
  container.innerHTML = "";

  const fragment = document.createDocumentFragment();

  list.forEach((item) => {
    const card = document.createElement("div");
    card.classList.add("strand-degree-card");

    const isDegree = item.category?.toLowerCase().includes("degree");
    const iconClass = isDegree ? "fa-graduation-cap" : "fa-school-flag";

    card.innerHTML = `
      <div class="card-header">
        <div class="strandegree-icon ${isDegree ? "degree" : "strand"}">
          <i class="fa-solid ${iconClass}"></i>
        </div>
        <div class="title-block">
          <span class="code">${item.code || ""}</span>
          <h3 class="program-name">${item.name}</h3>
        </div>
      </div>
      <p class="subtitle">${isDegree ? "College Degree" : "Senior High Strand"}</p>
      <div class="details">
        <p>${item.details || "No description available."}</p>
        ${
          isDegree
            ? `<p><strong>Duration:</strong> ${item.duration + ' years' || "N/A"}</p>`
            : ""
        }
      </div>
      <button class="view-btn" data-id="${item.idPk}">
        <i class="fa-solid fa-circle-info"></i> View Details
      </button>
    `;

    fragment.appendChild(card);
  });

  container.appendChild(fragment);
}

document.addEventListener("DOMContentLoaded", () => {
  const searchInput = document.getElementById("searchInput");

  searchInput.addEventListener("input", () => {
    const searchValue = searchInput.value.toLowerCase().trim();

    const filtered = allStrandegrees.filter(
      (item) =>
        item.name.toLowerCase().includes(searchValue) ||
        item.code.toLowerCase().includes(searchValue)
    );

    renderStrandegrees(filtered);
  });
});
