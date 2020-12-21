window.onload = fetch_student;

async function fetch_student(){
    let response = await fetch('api/student/display', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json;charset=utf-8'
        },
        body: JSON.stringify({
            email: localStorage.getItem("email"),
        })
    });
    let result = await response.json();
    console.log(result);
    localStorage.setItem("idd",result["student_id"]);
    document.getElementById('roll').innerHTML=result["roll_number"];
    document.getElementById('fn').innerHTML=result["first_name"];
    document.getElementById('ln').innerHTML=result["last_name"];
    document.getElementById('em').innerHTML=result["email"];
    document.getElementById('cg').innerHTML=result["cgpa"];
    document.getElementById('tc').innerHTML=result["total_credits"];
    document.getElementById('gy').innerHTML=result["graduation_year"];
    /*let courses_option = document.getElementById('courses');
    courses_option.innerHTML = '<option value=""> Choose...</option>';

    for(let i = 0 ; i<courses.length ; i++){
        courses_option.innerHTML += '<option value="'+courses[i]+'">'+courses[i]+'</option>';
    }*/
}