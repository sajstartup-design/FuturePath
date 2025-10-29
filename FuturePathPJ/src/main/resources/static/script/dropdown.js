const dropdowns = document.querySelectorAll(".dropdown");
	
if (dropdowns.length > 0) {
   dropdowns.forEach((dropdown) => {
      createCustomDropdown(dropdown);
   });
}

function createCustomDropdown(dropdown){

	// Get All Select Options
	   // And Convert them from NodeList to Array
	   const options = dropdown.querySelectorAll("option");
	   const optionsArr = Array.prototype.slice.call(options);
	   
	   const inputWrapper = dropdown.closest('.input-wrapper');
	   
	   

	   // Create Custom Dropdown Element and Add Class Dropdown
	   const customDropdown = document.createElement("div");
	   customDropdown.classList.add("dropdown");
	   inputWrapper.appendChild(customDropdown);

	   // Create Element for Selected Option
	   const selected = document.createElement("div");
	   selected.classList.add("dropdown-select");
	   const selectedOption = optionsArr.find(opt => opt.selected) || optionsArr[0];
	   selected.textContent = selectedOption.textContent;
	   customDropdown.appendChild(selected); 

	   // Create Element for Dropdown Menu
	   // Add Class and Append it to Custom Dropdown
	   const menu = document.createElement("div");
	   menu.classList.add("dropdown-menu");
	   customDropdown.appendChild(menu);
	   selected.addEventListener("click", toggleDropdown.bind(menu));
	   
	   const search = document.createElement("input");
	   search.placeholder = "Search...";
	   search.type = "text";
	   search.classList.add("dropdown-menu-search");
	   menu.appendChild(search);

	   // Create Wrapper Element for Menu Items
	   // Add Class and Append to Menu Element
	   const menuInnerWrapper = document.createElement("div");
	   menuInnerWrapper.classList.add("dropdown-menu-inner");
	   menu.appendChild(menuInnerWrapper);

	   // Loop All Options and Create Custom Option for Each Option
	   // And Append it to Inner Wrapper Element
	   let lastCodeGroup = null;
	   optionsArr.forEach((option) => {
	     const text = option.textContent.trim();
	     const match = text.match(/^([A-Z]-[A-Z]-[A-Z])/); // detect RIASEC code at start
	     const codeGroup = match ? match[1] : null;

	     // Only create group headers if dropdown contains codes (like R-I-C)
	     if (codeGroup && codeGroup !== lastCodeGroup && option.value) {
	       const groupHeader = document.createElement("div");
	       groupHeader.classList.add("dropdown-group-header");
	       groupHeader.textContent = codeGroup;
	       menuInnerWrapper.appendChild(groupHeader);
	       lastCodeGroup = codeGroup;
	     }

	     // Create normal item
	     const item = document.createElement("div");
	     item.classList.add("dropdown-menu-item");
	     item.dataset.value = option.value;
	     item.innerHTML = `<i class="fas fa-circle"></i> ${text}`;
	     menuInnerWrapper.appendChild(item);

	     item.addEventListener("click", setSelected.bind(item, selected, dropdown, menu));
	   });

	   // Add Selected Class to First Custom Select Option
	   menuInnerWrapper.querySelector("div").classList.add("selected");

	   // Add Input Event to Search Input Element to Filter Items
	   // Add Click Event to Element to Close Custom Dropdown if Clicked Outside
	   // Hide the Original Dropdown(Select)
	   search.addEventListener("input", filterItems.bind(search, optionsArr, menu));
	   document.addEventListener(
	      "click",
	      closeIfClickedOutside.bind(customDropdown, menu)
	   );
	   dropdown.style.display = "none";
	}



function toggleDropdown() {
	   if (this.offsetParent !== null) {
	      this.style.display = "none";
	   } else {
	      this.style.display = "block";
	      this.querySelector("input").focus();
	   }
	}

function setSelected(selected, dropdown, menu) {
	   // Get Value and Label from Clicked Custom Option
	   const value = this.dataset.value;
	   const label = this.textContent;

	   // Change the Text on Selected Element
	   // Change the Value on Select Field
	   selected.textContent = label; 
	   dropdown.value = value;

	   // Close the Menu
	   // Reset Search Input Value
	   // Remove Selected Class from Previously Selected Option
	   // And Show All Div if they Were Filtered
	   // Add Selected Class to Clicked Option
	   menu.style.display = "none";
	   menu.querySelector("input").value = "";
	   menu.querySelectorAll("div").forEach((div) => {
	      if (div.classList.contains("is-select")) {
	         div.classList.remove("is-select");
	      }
	      if (div.offsetParent === null) {
	         div.style.display = "block";
	      }
	   });
	   this.classList.add("is-select");
	}

	// Filter the Items
	function filterItems(itemsArr, menu) {
	   // Get All Custom Select Options
	   // Get Value of Search Input
	   // Get Filtered Items
	   // Get the Indexes of Filtered Items
	   const customOptions = menu.querySelectorAll(".dropdown-menu-inner div");
	   const value = this.value.toLowerCase();
	   const filteredItems = itemsArr.filter((item) =>
	      item.textContent.toLowerCase().includes(value)
	   );
	   const indexesArr = filteredItems.map((item) => itemsArr.indexOf(item));

	   // Check if Option is not Inside Indexes Array
	   // And Hide it and if it is Inside Indexes Array and it is Hidden Show it
	   itemsArr.forEach((option) => {
	      if (!indexesArr.includes(itemsArr.indexOf(option))) {
	         customOptions[itemsArr.indexOf(option)].style.display = "none";
	      } else {
	         if (customOptions[itemsArr.indexOf(option)].offsetParent === null) {
	            customOptions[itemsArr.indexOf(option)].style.display = "block";
	         }
	      }
	   });
	}
	
	function closeIfClickedOutside(menu, e) {
	   if (
	      e.target.closest(".dropdown") === null &&
	      e.target !== this &&
	      menu.offsetParent !== null
	   ) {
	      menu.style.display = "none";
	   }
	}