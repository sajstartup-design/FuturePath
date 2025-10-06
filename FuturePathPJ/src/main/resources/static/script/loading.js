function createLoadingScreen(){
	
	const fragment = document.createDocumentFragment();
	
	const container = document.createElement('div');
	container.classList.add('loading-container');
	
	const loading = document.createElement('img');
	loading.src = '/gif/loading.gif';
	
	container.append(loading);
	fragment.append(container);
	
	return fragment;
}

/*function createLoadingScreenBody(){
	
	if (document.querySelector('.loading-background')) return; // already exists
	
	const BODY = document.body;
	
	const fragment = document.createDocumentFragment();
	
	const background = document.createElement('div');
	background.classList.add('loading-background');
		
	const loading = document.createElement('img');
	loading.src = '/gif/loading.gif';
	
	background.append(loading);
	fragment.append(background);
	
	BODY.append(fragment);
	
}	*/

function createLoadingScreenBody() {
  if (document.querySelector('.loader')) return; // already exists

  const BODY = document.body;

  const fragment = document.createDocumentFragment();

  const background = document.createElement('div');
  background.classList.add('loader');

  background.innerHTML = `
    <div class="loader-content">
      <img src="/gif/loading.gif">
      <h2>Future Path</h2>
      <p>"Your future is created by what you choose today."</p>
    </div>
  `;

  fragment.append(background);
  BODY.append(fragment);
}


function removeLoadingScreenBody() {
    const background = document.querySelector('.loader');
    if (background) {
        background.remove();
    }
}

function removeLoadingScreen(e){
	if(e){
		let loading = e.querySelector('.loading-container');
		loading.remove();
	}
}

document.addEventListener("DOMContentLoaded", () => {
	
	const buttons = document.querySelectorAll('.transitioning')
	
	buttons.forEach(btn => btn.addEventListener('click', function(){
		createLoadingScreenBody();
	}));
});