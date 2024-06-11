document.addEventListener("DOMContentLoaded",loaded);

async function loaded(event) { //aqui esperamos a que el dom este cargado totalmente para empezar el js
    try{ await menu();} catch(error){return;} //llamamos a la funcion que llama a todo para el js en el controller de bienvenida
}