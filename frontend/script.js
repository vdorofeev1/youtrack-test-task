// script.js
async function getRepos() {
  const url = 'http://localhost:8080/get_repos'
  try {
    const response = await fetch(url);
    const data = await response.json();
    handleData(data)
    //handleResponse(response.status, data); // Pass status code and parsed response body to handleResponse function
  } catch (error) {
    console.error('Error fetching data:', error);
  }
}

function handleData(data) {
  console.log(data);
  const resultContainer = document.getElementById('resultContainer');
  resultContainer.innerHTML = ''; // Clear previous content
  const repositories = data.body;
  for (const repo of Object.values(repositories)) {
    const repoName = repo.name;
    const hasHello = repo.hasHello;

    const repoElement = document.createElement('p');
    repoElement.textContent = repoName;

    if (hasHello) {
      repoElement.style.color = 'red';
    }

    resultContainer.appendChild(repoElement);
  }

}

document.addEventListener('DOMContentLoaded', function () {
  const organization = document.getElementById('organization');
  const token = document.getElementById('token');
  const goButton = document.getElementById('goButton');

  goButton.addEventListener('click', function () {
    console.log("pressed")
    getRepos()
  });
});
