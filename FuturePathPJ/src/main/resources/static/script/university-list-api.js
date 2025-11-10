createLoadingScreenBody();
loadUniversities();

async function loadUniversities() {
  try {
    const url = `/api/universities/retrieve`;

    const response = await fetch(url);
    const data = await response.json();

    console.log(data);
    const container = document.querySelector(".university-list");
    container.innerHTML = ""; // Clear previous content

    const fragment = document.createDocumentFragment();

    data.allUniversities.forEach((uni) => {
      const card = document.createElement("div");
      card.classList.add("university-card");

      const iconClass = getUniversityIcon(uni.category);

      // Fallback values
      const founded = uni.founded || "N/A";
      const students = uni.students ? `${uni.students}+` : "N/A";
      const category = uni.category || "N/A";
	  const courses = uni.courses ? `${uni.courses} Programs Available` : "No programs listed";

      const location = [uni.street, uni.city, uni.province, uni.postalCode]
        .filter(Boolean)
        .join(", ");

		card.innerHTML = `
		  <div class="uni-header">
		    <div class="uni-icon">
		      <i class="fa-solid ${iconClass}"></i>
		    </div>
		    <h3>${uni.universityName}</h3>
		    <span class="uni-type-badge ${category.toLowerCase()}">${category}</span>
		  </div>
		  <p class="location">
		    <i class="fa-solid fa-location-dot"></i> ${location || "No location"}
		  </p>
		  <div class="details">
		    <p><strong>Founded:</strong> ${founded}</p>
		    <p><strong>Students:</strong> ${students}</p>
		    <p><strong>Courses:</strong> ${courses}</p>
		  </div>
		  <a href="/universities/details?idPk=${uni.idPk}" class="view-btn" data-id="${uni.idPk}">
		    <i class="fa-solid fa-circle-info"></i> View Details
		  </a>
		`;

	
      fragment.appendChild(card);
    });

    container.appendChild(fragment);
    removeLoadingScreenBody();
  } catch (error) {
    console.error("Error fetching universities:", error);
  }
}

// Choose an icon based on university type
function getUniversityIcon(type = "") {
  const lower = type.toLowerCase();
  if (lower.includes("PUBLIC")) return "fa-landmark";
  if (lower.includes("PRIVATE")) return "fa-building-columns";
  return "fa-graduation-cap";
}
