var backend="http://localhost:8080/api";
var api_login=backend+'/login';

var loginstate ={
    logged: false,
    user : {email:"", name:"", rol:""}
}

async function checkuser(){
    let request = new Request(api_login+'/current-user', {method: 'GET'});
    const response = await fetch(request);
    if (response.ok) {
        loginstate.logged = true;
        loginstate.user = await response.json();
    }
    else {
        loginstate.logged = false;
    }
}

async function menu(){
    await checkuser();
    if (!loginstate.logged
        && document.location.pathname != "/pages/bienvenida/view.html") {
        document.location = "/pages/bienvenida/view.html";
        throw new Error("Usuario no autorizado");
    }
    render_menu();
}

function render_menu() {
    if (!loginstate.logged) {
        html = `
            <div class="logo">
            <img src="/images/logo.png">
                <span>Meetings</span>
            </div>
            <div>
                <ul class="Menu">
                    <li id="loginlink"><a href="#"> Login</a></li>
                </ul>
            </div>
        `;
        html2 = `
            <div class="Footer">
            <div class="logoF">
                <span>Total Soft Inc.</span>
                <span> 2023 TSF Inc.</span>
            </div>
        `;
        document.querySelector('#menu').innerHTML = html;
        document.querySelector('#footer').innerHTML = html2;
        document.querySelector("#menu #loginlink").addEventListener('click', ask);
        render_loginoverlay();
        render_loginview();
    }
    else {
        html = `
            <div class="logo">
                <span>Meetings</span>
                <img src="/images/logo.png">
            </div>
            <div>
                <ul class="Menu">
                    <li id="bienvenidalink"><a href="#"> Bienvenida</a></li>
                    <li id="meetingslink"><a href="#"> Meetings</a></li>
                    <li id="logoutlink"><a href="#"> Logout</a></li>
                </ul>
            </div>
            <div class="user">&nbsp &nbsp ${loginstate.user.email}</div>
        `;
        html2 = `
            <div class="Footer">
            <div class="logoF">
                <span>Total Soft Inc.</span>
                <span> 2023 TSF Inc.</span>
            </div>
        `;
        document.querySelector('#menu').innerHTML = html;
        document.querySelector('#footer').innerHTML = html2;
        document.querySelector("#menu #logoutlink").addEventListener('click', logout);
        document.querySelector("#menu #bienvenidalink").addEventListener('click', e => {
            document.location = "/pages/bienvenida/view.html";
        });
        document.querySelector("#menu #meetingslink").addEventListener('click', e => {
            document.location = "/pages/meetings/view.html";
        });
    }
}

function render_loginoverlay() {
    html = `
        <div id="loginoverlay" class="loginoverlay"></div>
    `;
    overlay=document.createElement('div');
    overlay.innerHTML=html;
    document.body.appendChild(overlay);
    document.querySelector("#loginoverlay").addEventListener("click",toggle_loginview);
}

function render_loginview() {
    html = `
    <div id="loginview" class='loginview'>
        <div class='col-12'>
            <div>
                <form name="formulario">
                    <div class='container'>
<!--                    <span class ="img"> <img src="/images/user.png"></span>           Luego agrego la imagen     -->  
                        <span class="close-btn">&times;</span> <!-- Funciona para agregar la x para cerrar el pop-up -->                   
                        <div class='row'><div class='col-12 text-centered cooper'>Login</div></div>
                        <div class='row'><div class='col-3 text-right'>Email</div><div class='col-6'><input type="text" name="id" id="id" value=""></div></div>
                        <div class='row'><div class='col-3 text-right'>Clave</div><div class='col-6'><input type="text" name="password" id="password" value=""></div></div>
                        <div class='row'>
                            <div class='col-6 text-centered cooper'>
                                <input id="login" class="boton" type="button" value="Login">
                            </div>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>    
    `;
    view=document.createElement('div');
    view.innerHTML=html;
    document.body.appendChild(view);
    document.querySelector("#loginview #login").addEventListener("click",login);
    document.querySelector('.close-btn').addEventListener('click', function() {
        document.getElementById("loginoverlay").classList.toggle("active");
        document.getElementById("loginview").classList.toggle("active");
    });
}

function ask(event){
    event.preventDefault();
    toggle_loginview();
    document.querySelectorAll('#loginview input').forEach( (i)=> {i.classList.remove("invalid");});
    document.querySelector("#loginview #id").value = "";
    document.querySelector("#loginview #password").value = "";
}

function toggle_loginview(){
    document.getElementById("loginoverlay").classList.toggle("active");
    document.getElementById("loginview").classList.toggle("active");
}

function login(){
    let user={email:document.getElementById("id").value,
        password:document.getElementById("password").value
    };
    let request = new Request(api_login+'/login', {method: 'POST',
        headers: { 'Content-Type': 'application/json'},
        body: JSON.stringify(user)});
    (async ()=>{
        const response = await fetch(request);
        if (!response.ok) {errorMessage(response.status);return;}
        document.location="/pages/meetings/view.html";
    })();
}

function logout(event){
    event.preventDefault();
    let request = new Request(api_login+'/logout', {method: 'POST'});
    (async ()=>{
        const response = await fetch(request);
        if (!response.ok) {errorMessage(response.status);return;}
        document.location="/pages/bienvenida/view.html";
    })();
}

function errorMessage(status,place){
    switch(status){
        case 404: error= "Registro no encontrado"; break;
        case 409: error="Registro duplicado"; break;
        case 401: error="Usuario no autorizado"; break;
        case 403: error="Usuario no tiene derechos"; break;
    }
    window.alert(error);
}


