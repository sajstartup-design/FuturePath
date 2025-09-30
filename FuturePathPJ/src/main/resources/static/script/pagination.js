var currentPage = 1;

function updatePagination(pagination) {
  const prevBtn = document.querySelector('.prev-btn');
  const nextBtn = document.querySelector('.next-btn');
  const pageBtns = document.querySelectorAll('.btn-page');

  if (!pagination) return;

  const totalPages = pagination.totalPages;
  let { hasNext, hasPrevious, page } = pagination;

  // update current page (backend is 0-based, so +1)
  currentPage = page + 1;

  // enable/disable prev & next
  nextBtn.disabled = !hasNext;
  prevBtn.disabled = !hasPrevious;

  // calculate window range
  let startPage = Math.max(1, currentPage - 2);
  let endPage = startPage + pageBtns.length - 1;
  if (endPage > totalPages) {
    endPage = totalPages;
    startPage = Math.max(1, endPage - (pageBtns.length - 1));
  }

  // update button text + state
  pageBtns.forEach((btn, idx) => {
    const pageNumber = startPage + idx;
    if (pageNumber <= totalPages) {
      btn.textContent = pageNumber;
      btn.disabled = false;
      //btn.style.display = "inline-block"; // make sure visible

      if (pageNumber === currentPage) {
        btn.classList.add("active");
      } else {
        btn.classList.remove("active");
      }
    } else {
      btn.textContent = "-";
      btn.disabled = true;
      //btn.style.display = "none"; // hide extra buttons
    }
  });
}
