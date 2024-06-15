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

    //document.getElementById("search").addEventListener("click",search);
    //document.getElementById("new").addEventListener("click",ask);

    //document.getElementById("itemoverlay").addEventListener("click",toggle_itemview);

    //document.querySelector("#itemview #registrar").addEventListener("click",add);
    //document.querySelector("#itemview #cancelar").addEventListener("click",toggle_itemview);

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


function ask(){
    empty_item();
    toggle_itemview();
    state.mode="ADD";
}

function toggle_itemview(){
    document.getElementById("itemoverlay").classList.toggle("active");
    document.getElementById("itemview").classList.toggle("active");
}

function empty_item(){
    state.item = {id:"", title:"", state:"", date:"", owner:""};
    state.contacts = [];
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




function add(){
    state.item.title = document.getElementById("title").value;
    state.item.state = document.getElementById("state").value;
    state.item.date = document.getElementById("date").value;
    state.item.owner = document.getElementById("owner").value;
    state.item.contacts = document.getElementById("contacts").value;

    let request = new Request(api, {method: 'POST',
        headers: { 'Content-Type': 'application/json'},
        body: JSON.stringify(state.item)});
    (async ()=>{
        const response = await fetch(request);
        if (!response.ok) {errorMessage(response.status);return;}
        fetchAndList();
        toggle_itemview();
    })();
}
