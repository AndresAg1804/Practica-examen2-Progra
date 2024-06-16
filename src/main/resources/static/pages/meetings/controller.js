var api=backend+'/meetings';

var state ={
    list: new Array(),
    item : {id:"", title:"", state:"", date:"", owner:""},
    contacts:[],
    contactsU:[],
    mode: "" // ADD, EDIT
}

document.addEventListener("DOMContentLoaded",loaded);

async function loaded(event){
    try{ await menu();} catch(error){return;}

    document.querySelector("#addImage").addEventListener("click",ask);

    document.querySelector("#itemview #aplicar").addEventListener("click", add);

    document.querySelector('.close-btn1').addEventListener("click", toggle_itemview);

    fetchAndList();
}

function fetchAndList(){
    const request = new Request(api, {method: 'GET', headers: { }});
    (async ()=>{
        const response = await fetch(request);
        if (!response.ok) {errorMessage(response.status);return;}
        state.list = await response.json();
        render_list();
    })();
}

function render_list(){
    var listado=document.getElementById("listMeetings");
    listado.innerHTML="";
    state.list.forEach( item=>render_list_item(listado,item));
}

function render_list_item(listado,item){
    var tr =document.createElement("tr");
    tr.innerHTML=`<td>${item.title}</td>
					<td>${item.state}</td>
                    <td>${item.date}</td>`;
    // Añade el evento de click a toda la fila
    tr.addEventListener("click", async function(){
        await loadContacts(item.id);
        handleRowClick(item);
    });
    listado.append(tr);
}

async function loadContacts(id){
    const request = new Request(api+'/contacts'+`?idM=${id}`, {method: 'GET', headers: { }});
    const response = await fetch(request);
    if (!response.ok) {errorMessage(response.status);return;}
    state.contacts = await response.json();

}

async function loadContactsByUser(){
    const request = new Request(api+'/contacts/byUser', {method: 'GET', headers: { }});
    const response = await fetch(request);
    if (!response.ok) {errorMessage(response.status);return;}
    state.contacts = await response.json();
}

async function ask(){
    empty_item();
    await loadContactsByUser();
    let stateSelect = document.getElementById("stateMeet");
    stateSelect.innerHTML = '';
    let options = ['UPCOMING', 'PUBLISHED', 'OVERDUE'];
    options.forEach(option => {
        let opt = document.createElement('option');
        opt.value = option;
        opt.textContent = option;
        stateSelect.appendChild(opt);
    });
    let emailSelect = document.getElementById("contactMeet");
    let img = document.getElementById("edit5");
    img.addEventListener("click",addEContact);
    state.contacts.forEach( contacto => {
        const option = document.createElement('option');
        option.value = contacto.email;
        option.textContent = contacto.email;
        emailSelect.appendChild(option);
    });
    let img2 = document.getElementById("edit6");
    img2.addEventListener("click",addNContact);
    toggle_itemview();
    state.mode="ADD";
}

function addEContact(){
    let contact = {};
    contact.email=document.getElementById('contactMeet').value;
    state.contactsU.push(contact);
    updateContactList();
}
function addNContact(){
    let contact = {};
    contact.email=document.getElementById('contactCorreo').value;
    state.contactsU.push(contact);
    updateContactList();
}

function updateContactList(){
    var listadoC=document.getElementById("contacts-list");
    listadoC.innerHTML="";
    state.contactsU.forEach( itemCExt=>updateContactListItem(listadoC,itemCExt));
}

function updateContactListItem(listadoC,itemCExt){
    var tr =document.createElement("tr");
    tr.innerHTML=`
    <td>${itemCExt.email}</td>
    <td id="editC"><img src="../../images/remove.png" id="edit7"></td>
    `;
    tr.querySelector("#editC").addEventListener("click",()=>{deleteContact(itemCExt);});
    listadoC.append(tr);
}

function deleteContact(contact){
    state.contactsU = state.contactsU.filter(c => c.email !== contact.email);
    updateContactList();
}

function toggle_itemview(){
    document.getElementById("itemoverlay").classList.toggle("active");
    document.getElementById("itemview").classList.toggle("active");
}

function empty_item(){
    state.item = {id:"", title:"", state:"", date:"", owner:""};
    state.contacts = [];
}
function empty_everything(){
    empty_item();
    state.contactsU = [];
    document.getElementById("titleMeet").value = "";
    document.getElementById("stateMeet").value = "";
    document.getElementById("dateMeet").value = "";
    document.getElementById("contactMeet").value = "";
    document.getElementById("contactCorreo").value = "";
    document.getElementById("contacts-list").innerHTML = "";
}
function handleRowClick(item){
    state.item = item;
    render_item();
    // toggle_itemview();
}


function render_item(){
    document.getElementById("titleForm").textContent = state.item.title;
    document.getElementById("stateForm").textContent = state.item.state;
    document.getElementById("dateForm").textContent = state.item.date;
    let contacts = document.getElementById("contactForm");
    contacts.innerHTML='';

    state.contacts.forEach( contact => {
        let contactDiv = document.createElement('div');
        contactDiv.textContent = contact.email;
        contactDiv.className='contact';
        contacts.appendChild(contactDiv);
        contacts.appendChild(document.createElement('br'));
    });
}


function addContacts(){
    state.contactsU.forEach(function(c) {
        c.meeting=state.item;
    });
    let request=new Request(api+'/addContacts', {method:'POST',
        headers:{'CONTENT-Type': 'application/json'},
        body: JSON.stringify(state.contactsU)});
    (async ()=>{
        const response= await fetch(request);
        if(!response.ok){errorMessage(response.status);return;}
        empty_everything();
    })();
}


function add(){
    state.item.title = document.getElementById("titleMeet").value;
    state.item.state = document.getElementById("stateMeet").value;
    state.item.date = document.getElementById("dateMeet").value;
    state.item.owner = loginstate.user;
    let request = new Request(api, {method: 'POST',
        headers: { 'Content-Type': 'application/json'},
        body: JSON.stringify(state.item)});
    (async () => {
        const response = await fetch(request);
        if (!response.ok) {
            errorMessage(response.status);return;
        }
        state.item = await response.json();
        await addContacts();
        toggle_itemview();
        fetchAndList();
    })();
}

