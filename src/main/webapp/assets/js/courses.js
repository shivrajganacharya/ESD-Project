async function fetch_data() {
    let response = await fetch('api/student/courses', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json;charset=utf-8'
        },
        body: JSON.stringify({
            email: localStorage.getItem("email"),
        })
    });

    let commits = await response.json(); // read response body and parse as JSON
    // alert(commits[0].author.login);
    console.log(commits);
    let table_data = document.getElementById("table_id_data");
    table_data.innerHTML = '';

    for (let i = 0; i < commits.length; i++) {
        let tr_data = document.createElement('tr');
        tr_data.innerHTML = '<td>' + (i) + '</td>\n' +
            '            <td>' + commits[i][0] + '</td>\n' +
            '            <td>' + commits[i][1] + '</td>\n' +
            '            <td>' + commits[i][2] + '</td>';
        table_data.appendChild(tr_data);

    }
}