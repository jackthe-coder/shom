// JavaScript Document

/*eslint-env es6*/ 

//selectores
const todoInput = document.querySelector(".todo-input");
const todoButton = document.querySelector(".todo-button");
const todoList = document.querySelector(".todo-list");
const filterOption = document.querySelector(".filter-todo")


//Event Listners

todoButton.addEventListener("click", addTodo);
todoList.addEventListener("click",deleteCheck);
filterOption.addEventListener("click",filterTodo);




 
//Functions

function addTodo(event){
	event.preventDefault();
	//toDo div
	const todoDiv = document.createElement("div");
	todoDiv.classList.add("todo");
	// creat Li
	const newTodo = document.createElement('li');
	newTodo.innerText = todoInput.value;
	//if (newTodo.innerText==""){}
	newTodo.classList.add("todo-item");
	todoDiv.appendChild(newTodo);
	//Check mark button 
	const completedButton = document.createElement('button');
	completedButton.innerHTML = '<i class="fas fa-check"></i>';
	completedButton.classList.add("complete-btn");
	todoDiv.appendChild(completedButton);
	//Check trash button
	const trashButton = document.createElement('button');
	trashButton.innerHTML = '<i class="fas fa-trash"></i>';
	trashButton.classList.add("trash-btn");
	todoDiv.appendChild(trashButton);
	//Chkeck category button
	const category = document.createElement("button")
	category.innerHTML = '<i class="fab fa-buffer"></i>'
	category.classList.add("category-btn");
	todoDiv.appendChild(category);
	
	//append to list 
	todoList.appendChild(todoDiv);
	
	
	//clear todo input value
	todoInput.value="";	
}


function deleteCheck(e){
	const item = e.target;
	//delete todo
	if(item.classList[0] === 'trash-btn'){
		const todo = item.parentElement;
		//animation
		todo.classList.add("fall");
		todo.addEventListener('transitionend',function(){
			todo.remove();
		})
		
	}
	//Check mark
	
	if(item.classList[0] === 'complete-btn'){
		const todo = item.parentElement;
		todo.classList.toggle("completed");
	}
	
	//check category 
	if(item.classList[0]==='category-btn'){
		const todo = item.parentElement;
		const categories = document.createElement("button")
		categories.innerHTML = '<i class="fas fa-hand-sparkles"></i>'
		categories.classList.add("chores");
		todo.appendChild(categories);
		
	}
	/*if(item.classList[0]==='chores'){
		const todo = item.parentElement;
		const categories = document.createElement("button")
		categories.innerHTML = '<i class="fas fa-shopping-bag"></i>'
		categories.classList.toggle("shopping");
		todo[3]=categories;
		
	}*/
	
	
}

function filterTodo(e){
	const todos = todoList.childNodes
	todos.forEach(function(todo){
		switch(e.target.value){
			case "all":
				todo.style.display = "flex";
				break;
			case "completed":
				if(todo.classList.contains('completed')){
					todo.style.display = "flex";
				}else{
					todo.style.display = "none";
				}		
				break;
			case "uncompleted":
				if(!todo.classList.contains('completed')){
					todo.style.display = "flex";
				}else{
					todo.style.display = "none";
				}
				break;
		}
	})
}


