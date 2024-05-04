async function getRepos(organization, token) {
  const organizationValue = organization.value;
  const tokenValue = token.value;
  const url = `http://localhost:8080/get_repos_with_params?token=${tokenValue}&link=${organizationValue}`;
  try {
    const response = await fetch(url);
    const data = await response.json();
    handleData(data);
  } catch (error) {
    console.error('Error fetching data:', error);
  }
}

function handleData(data) {
  console.log(data);
  const resultContainer = document.getElementById('resultContainer');
  resultContainer.innerHTML = '';
  console.log(data.statusCode)
  if (data.statusCode === 200) {
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
  } else {
    const errorMessage = document.createElement('p');
    errorMessage.textContent = data.message;
    resultContainer.appendChild(errorMessage);
  }

}

document.addEventListener('DOMContentLoaded', function () {
  const organization = document.getElementById('organization');
  const token = document.getElementById('token');
  const goButton = document.getElementById('goButton');

  goButton.addEventListener('click', function () {
    console.log("pressed")
    getRepos(organization, token)
  });
})
