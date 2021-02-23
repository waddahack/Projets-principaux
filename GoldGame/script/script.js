window.addEventListener("load", initVariables);

function initVariables(){
	// VARIABLES
	var classeur = document.getElementsByTagName("header")[0];
	var arrayOnglets = toArray(classeur.getElementsByClassName("onglet"));
	var ongletAnnuler = document.getElementById("annuler");
	ongletAnnuler.addEventListener("click", finChoixMine);
	var arraySections = toArray(document.getElementsByTagName("section"));
	var arrayButtonsAchat = toArray(document.getElementsByClassName("butAchat"));
	var arrayButtonsErreur = toArray(document.getElementsByClassName("butErreur"));
	var arrayInvAffiche = toArray(arraySections[1].getElementsByClassName("stuff"));
	arrayInvAffiche.unshift(arraySections[2].getElementsByClassName("stuff")[0]);
	arraySections[2].getElementsByClassName("paragraphe")[0].style.backgroundColor = "rgba(0,0,0,0)";
	arraySections[2].getElementsByClassName("paragraphe")[0].style.border = "none";
	var arrayPrixAffiche = toArray(arraySections[2].getElementsByClassName("prix"));
	var arrayValsMineAffiche = toArray(arraySections[0].getElementsByClassName("stuff"));
	var arrayPioches = toArray(document.getElementsByClassName("pioche"));
	var arrayButtonsChoisir = toArray(document.getElementsByClassName("butChoisir"));

	var arrayValDepart = [0, 5, 10, 10, 50];
	var arrayInv = [0, 1, 10];
	var arrayPrix = [5, 20, 1, 10, 1000, 10, 100];
	var nbAttaques = 0;
	
	var selectedOnglet = 0;
	var selectedAchat;

	for(let i = 0 ; i < arrayOnglets.length ; i++)
		arrayOnglets[i].addEventListener("click", function(){selectOnglet(i);});
	selectOnglet(0);

	for(let i = 0 ; i < arrayButtonsAchat.length ; i++){
		arrayButtonsAchat[i].id = i;
		arrayButtonsAchat[i].clicked = false;
		arrayButtonsAchat[i].addEventListener("click", acheter);
		arrayButtonsAchat[i].boxShadow = "none";
	}

	for(let i = 0 ; i < arrayPioches.length ; i++){
		arrayPioches[i].id = i;
		arrayPioches[i].buttonMiner = arrayPioches[i].getElementsByClassName("butMiner")[0];
		arrayPioches[i].buttonMiner.addEventListener("click", function(){miner(arrayPioches[i]);});
		arrayPioches[i].buttonChoisir = arrayPioches[i].getElementsByClassName("butChoisir")[0];
		arrayPioches[i].buttonChoisir.addEventListener("click", function(){mineChoisit(getButtonAchatClicked(), arrayPioches[i]);});
		arrayPioches[i].chargement = arrayPioches[i].getElementsByClassName("chargement")[0];
		arrayPioches[i].arrayQuarts = toArray(arrayPioches[i].getElementsByClassName("unQuart"));
		arrayPioches[i].nbOuvrier = arrayValDepart[0];
		arrayPioches[i].nbOuvrierMax = arrayValDepart[1];
		arrayPioches[i].tauxReussite = arrayValDepart[2];
		arrayPioches[i].nbPalier = arrayValDepart[3];
		arrayPioches[i].palierDeleted = false;
		arrayPioches[i].update = updateMine;
	}

	updateInventaire(arrayPioches[0]);
	updatePrix();

	setTimeout(function(){
		let i = arrayPioches[0].arrayQuarts.length-1;
		charger(arrayPioches[0], i);
	}, 250);

	setTimeout(function(){
		attaque();
	}, 250);

	function charger(pioche, index){
		if(index == -1 && !pioche.palierDeleted){
			for(let i = 0 ; i < pioche.arrayQuarts.length ; i++){
				pioche.arrayQuarts[i].style.backgroundColor = "rgba(255, 255, 0, 0.8)";
				pioche.arrayQuarts[i].style.boxShadow = "none";
			}
			index = pioche.arrayQuarts.length-1;
			pioche.chargement.style.boxShadow = "0 0 20px rgb(255, 255, 50)";
			minageOuvrier(pioche);
			setTimeout(function(){
				for(let i = 0 ; i < pioche.arrayQuarts.length ; i++)
					pioche.arrayQuarts[i].style.backgroundColor = "white";
				pioche.chargement.style.boxShadow = "none";	
			}, 249);
		}
		else{
			if(!pioche.palierDeleted){
				if(pioche.arrayQuarts[index] != null){
					pioche.arrayQuarts[index].style.backgroundColor = "rgb(0,255,0)";
					pioche.arrayQuarts[index].style.boxShadow = "0 -10px 10px rgb(0, 255, 0)";
				}
				index -= 1;
			}
		}

		if(pioche.style.display != "none")
			setTimeout(function(){
				charger(pioche, index);
			},250);
	}

	function attaque(){
		let rand = Math.floor(Math.random()*Math.floor(100))+1;
		if(rand <= arrayValDepart[4]){
			if(arrayPioches.length > 0) spoilerAlerte();
		}
		setTimeout(function(){
			attaque();
		}, 1000);
	}

	function selectOnglet(index){
		selectedOnglet = index;
		for(let i = 0 ; i < arrayOnglets.length ; i++){
			if(i == index) arraySections[i].style.display = "flex";
			else arraySections[i].style.display = "none";
			arrayOnglets[i].style.backgroundColor = "rgba(230,230,0,0.2)";
		}

		arrayOnglets[index].style.backgroundColor = "rgba(255,255,255,0.2)";
	}

	function miner(pioche){
		if(pioche.arrayQuarts[pioche.arrayQuarts.length-1].style.backgroundColor == "rgba(255, 255, 0, 0.8)"){
			arrayInv[0] += arrayInv[1];
			updateGold();
			
			pioche.buttonMiner.style.boxShadow = "0 0 30px rgba(255, 255, 0, 0.8)";
			setTimeout(function(){pioche.buttonMiner.style.boxShadow = "none";}, 500);
		}
		else if(arrayInv[2] > 0){
			piocheCassee(pioche);
			setTimeout(function(){
				piocheRepare(pioche);
			}, (arrayInv[2]*1000));
		}
	}

	function minageOuvrier(pioche){
		for(let i = 0 ; i < pioche.nbOuvrier ; i++){
			let rand = Math.floor(Math.random()*Math.floor(100))+1;
			if(rand <= pioche.tauxReussite){
				arrayInv[0] += 1;
				updateGold();
			}
		}
	}

	function acheter(evt){
		let button = evt.target;
		if(button.className != "butAchat") button = button.parentElement;
		switch(button.id){
			case "2" : // Temps de réparation de la pioche
				if(arrayInv[0] >= arrayPrix[2]){
					arrayInv[0] = Math.round(arrayInv[0]-arrayPrix[2]);                  // arrayInv[0] = argent
					arrayInv[2] -= 1;                                                    // arrayInv[1] = nb gold / coup pioche
					arrayPrix[2] = Math.round(arrayPrix[2]*2);                         // arrayInv[2] = tps rep pioche
					if(arrayInv[2] == 0) disableButton(button, "Pioche incassable");     // arrayValDepart[0] = ouvriers
					else butClicked(button);											 // arrayValDepart[1] = ouvriers max
				}																		 // arrayValDepart[2] = taux reussite
				else incorrect(button, "Pépites insuffisantes");						 // arrayValDepart[3] = nbPalier
				updatePrix();
				updateInventaire(null);
				break;																     // arrayPrix[0] = palier
			case "0" : // Nombre de palier de chargement -> Propre à la mine 			 // arrayPrix[1] = nb gold / coup pioche
				if(arrayInv[0] >= arrayPrix[0]){										 // arrayPrix[2] = tps rep pioche
					button.clicked = true;												 // arrayPrix[3] = ouvriers
					choixMine(button, arrayPrix[0]);													 // arrayPrix[4] = ouvriers max
				}																		 // arrayPrix[5] = taux reussite
				else incorrect(button, "Pépites insuffisantes");						 // arrayPrix[6] = mine
				break;
			case "6" : // Nombre de mine
				if(arrayInv[0] >= arrayPrix[6]){										
					arrayInv[0] = Math.round(arrayInv[0]-arrayPrix[6]);
					arrayPrix[6] = Math.round(arrayPrix[6]*1.5);
					creerPioche();
					butClicked(button);
					arrayValDepart[4] += 0.2;
				}
				else incorrect(button, "Pépites insuffisantes");
				updatePrix();
				updateInventaire(null);
				break;
			case "3" : // ouvrier -> Propre à la mine
				if(arrayInv[0] >= arrayPrix[3]){
					button.clicked = true;
					choixMine(button, arrayPrix[3]);
				}
				else incorrect(button, "Pépites insuffisantes");
				break;
			case "5" : // Taux de réussite -> Propre à la mine
				if(arrayInv[0] >= arrayPrix[5]){
					button.clicked = true;
					choixMine(button, arrayPrix[5]);
				}
				else incorrect(button, "Pépites insuffisantes");
				break;
			case "1" : // Pioche mine double
				if(arrayInv[0] >= arrayPrix[1]){
					arrayInv[0] = Math.round(arrayInv[0]-arrayPrix[1]);
					arrayInv[1] *= 2;
					arrayPrix[1] = Math.round(arrayPrix[1]*3);
					butClicked(button);
				}
				else incorrect(button, "Pépites insuffisantes");
				updatePrix();
				updateInventaire(null);
				break;
			case "4" : // Ouvriers max -> Propre à la mine
				if(arrayInv[0] >= arrayPrix[4]){
					button.clicked = true;
					choixMine(button, arrayPrix[4]);
				}
				else incorrect(button, "Pépites insuffisantes");
				break;
		}
	}

	function updateGold(){
		arrayInvAffiche[0].textContent = Math.round(arrayInv[0]);
		arrayInvAffiche[1].textContent = Math.round(arrayInv[0]);
		document.getElementById("pepites").textContent = "Pépites : " + Math.round(arrayInv[0]);
	}

	function updateMine(){
		let arrayVals = arrayValsMineAffiche.slice();
		let vals = arrayVals.splice(this.id*3, 3);
		vals[0].textContent = this.nbOuvrier;
		vals[1].textContent = this.nbOuvrierMax;
		vals[2].textContent = this.tauxReussite;
	}

	function updateInventaire(){
		for(let i = 0 ; i < 3 ; i++)
			arrayInvAffiche[i+1].textContent = arrayInv[i];
		arrayInvAffiche[0].textContent = Math.round(arrayInv[0]);
	}

	function updatePrix(){
		for(let i = 0 ; i < arrayPrixAffiche.length ; i++){
			if(arrayPrix[i] >= 1000)
				arrayPrixAffiche[i].textContent = Math.round((arrayPrix[i]/1000)*10)/10+" k";
			else
				arrayPrixAffiche[i].textContent = arrayPrix[i];
		}
	}

	function creerPioche(){
		var pioche = document.createElement("div");
		pioche.setAttribute("class", "pioche");
		var butChoisir = document.createElement("button");
		butChoisir.textContent = "Choisir";
		butChoisir.setAttribute("class", "butChoisir");
		var butMiner = document.createElement("button");
		butMiner.textContent = "Miner";
		butMiner.setAttribute("class", "butMiner");
		var chargement = document.createElement("div");
		chargement.setAttribute("class", "chargement");
		for(let i = 0 ; i < 10 ; i++){
			var quart = document.createElement("div");
			quart.setAttribute("class", "unQuart");
			chargement.appendChild(quart);
		}
		var ouvriers = document.createElement("div");
		ouvriers.setAttribute("class","paragraphe");
		var reussite = document.createElement("div");
		reussite.setAttribute("class","paragraphe");
		var nbOuv = document.createElement("div");
		nbOuv.setAttribute("class","stuff");
		nbOuv.textContent = 0;
		var nbOuvMax = document.createElement("div");
		nbOuvMax.setAttribute("class","stuff");
		nbOuvMax.textContent = 5;
		var taux = document.createElement("div");
		taux.setAttribute("class","stuff");
		taux.textContent = 10;
		ouvriers.appendChild(nbOuv);
		var slash = document.createElement("div");
		slash.textContent = " / ";
		ouvriers.appendChild(slash);
		ouvriers.appendChild(nbOuvMax);
		var mot = document.createElement("div");
		mot.textContent = " ouvriers";
		ouvriers.appendChild(mot);
		reussite.appendChild(taux);
		var pourcent = document.createElement("div");
		pourcent.textContent = "%";
		reussite.appendChild(pourcent);

		pioche.appendChild(butChoisir);
		pioche.appendChild(butMiner);
		pioche.appendChild(chargement);
		pioche.appendChild(ouvriers);
		pioche.appendChild(reussite);
		
		pioche.id = arrayPioches.length;
		pioche.buttonMiner = butMiner;
		pioche.buttonMiner.addEventListener("click", function(){miner(pioche);});
		pioche.buttonChoisir = butChoisir;
		pioche.chargement = chargement;
		pioche.arrayQuarts = toArray(chargement.getElementsByClassName("unQuart"));
		pioche.nbOuvrier = arrayValDepart[0];
		pioche.nbOuvrierMax = arrayValDepart[1];
		pioche.tauxReussite = arrayValDepart[2];
		pioche.nbPalier = arrayValDepart[3];
		pioche.palierDeleted = false;
		pioche.update = updateMine;
		pioche.buttonChoisir.addEventListener("click", function(){mineChoisit(getButtonAchatClicked(), pioche);});

		updateArraysAjout(pioche);
		arraySections[0].appendChild(pioche);

		setTimeout(function(){
			let i = pioche.arrayQuarts.length-1;
			charger(pioche, i);
		}, 250);
	}

	function updateArraysAjout(pioche){
		arrayPioches.push(pioche);
		arrayButtonsChoisir.push(pioche.buttonChoisir);
		arrayValsMineAffiche.push(pioche.getElementsByClassName("stuff")[0]);
		arrayValsMineAffiche.push(pioche.getElementsByClassName("stuff")[1]);
		arrayValsMineAffiche.push(pioche.getElementsByClassName("stuff")[2]);
	}

	function butClicked(button){
		button.style.boxShadow = "0 0 15px rgba(255, 255, 0, 0.8)";
		button.disabled = true;
		setTimeout(function(){
			if(button.boxShadow != null) button.style.boxShadow = button.boxShadow;
			else button.style.boxShadow = "none";
			button.disabled = false;
		}, 500);
	}

	function disableButton(button, message){
		button.style.boxShadow = "none";
		button.boxShadow = "none";
		button.style.border = "solid 1px rgb(150, 150, 150)";
		button.disabled = true;
		button.textContent = message;
		button.style.cursor = "default";
	}

	function incorrect(button, message){
		if(message != button.textContent){
			arrayButtonsErreur[arrayButtonsAchat.indexOf(button)].textContent = message;
			arrayButtonsErreur[arrayButtonsAchat.indexOf(button)].style.display = "flex";
			button.style.display = "none";
			setTimeout(function(){
				button.style.display = "flex";
				arrayButtonsErreur[arrayButtonsAchat.indexOf(button)].style.display = "none";
			}, 1500);
		}
	}

	function choixMine(button, prix){
		selectOnglet(0);

		let pObj = document.getElementById("prixObjet");
		pObj.style.display = "flex";
		pObj.textContent = "Prix : " + prix;

		let pepete = document.getElementById("pepites");
		pepete.style.display = "flex";
		pepete.textContent = "Pépites : " + arrayInv[0];

		for(let i = 0 ; i < arrayOnglets.length ; i++)
			arrayOnglets[i].style.display = "none";
		ongletAnnuler.style.display = "flex";

		for(let i = 0 ; i < arrayPioches.length ; i++){
			arrayPioches[i].buttonChoisir.style.cursor = "pointer";
			arrayPioches[i].buttonMiner.style.display = "none";
			arrayPioches[i].buttonChoisir.style.display = "block";
		}
	}

	function finChoixMine(){
		selectOnglet(2);

		let pObj = document.getElementById("prixObjet");
		pObj.style.display = "none";
		pObj.textContent = "";

		let pepete = document.getElementById("pepites");
		pepete.style.display = "none";
		pepete.textContent = "";

		for(let i = 0 ; i < arrayOnglets.length ; i++)
			arrayOnglets[i].style.display = "flex";
		ongletAnnuler.style.display = "none";

		for(let i = 0 ; i < arrayPioches.length ; i++){
			arrayPioches[i].buttonChoisir.style.cursor = "default";
			arrayPioches[i].buttonMiner.style.display = "block";
			arrayPioches[i].buttonChoisir.style.display = "none";
		}
	}

	function mineChoisit(button, pioche){
		if(arrayInv[0] >= arrayPrix[button.id]){
			switch(button.id){
				case "0" : // Nombre de palier de chargement
					if(pioche.nbPalier > 2){
						arrayInv[0] = Math.round(arrayInv[0]-arrayPrix[0]);
						pioche.nbPalier -= 1;
						arrayPrix[0] += 2;
						pioche.palierDeleted = true;
						setTimeout(function(){
							pioche.palierDeleted = false;
							pioche = null;
						}, 500);
						pioche.arrayQuarts[0].style.display = "none";
						pioche.arrayQuarts.splice(0, 1);
					}
					else
						incorrect(pioche.buttonChoisir, "Paliers déjà au minimum");
					break;
				case "3" : // ouvrier
					if(pioche.nbOuvrier < pioche.nbOuvrierMax){
						arrayInv[0] = Math.round(arrayInv[0]-arrayPrix[3]);
						pioche.nbOuvrier += 1;
						arrayPrix[3] = Math.round(arrayPrix[3]*1.1);
					}
					else
						incorrect(pioche.buttonChoisir, "Mine déjà remplie");
					break;
				case "5" : // Taux de réussite
					if(pioche.tauxReussite < 100){
						arrayInv[0] = Math.round(arrayInv[0]-arrayPrix[5]);
						pioche.tauxReussite += 10;
						arrayPrix[5] = Math.round(arrayPrix[5]*1.2);;
					}
					else
						incorrect(pioche.buttonChoisir, "Mine déjà 100% productive");
					break;
				case "4" : // Ouvriers max
					arrayInv[0] = Math.round(arrayInv[0]-arrayPrix[4]);
					pioche.nbOuvrierMax += 5;
					arrayPrix[4] = Math.round(arrayPrix[4]*1.2);;
					butClicked(button);
					break;
			}
			butClicked(pioche.buttonChoisir);
			document.getElementById("prixObjet").textContent = "Prix : " + arrayPrix[button.id];
			document.getElementById("pepites").textContent = "Pépites : " + arrayInv[0];
			updatePrix();
			updateInventaire();
			pioche.update();
			arrayValDepart[4] += 0.2;
		}
		else{
			pioche.buttonChoisir.textContent = "Pépites insuffisantes";
			pioche.buttonChoisir.style.width = "120%";
			setTimeout(function(){pioche.buttonChoisir.textContent = "Choisir";pioche.buttonChoisir.style.width = "";}, 500);
		}
	}

	function getButtonAchatClicked(){
		for(let i = 0 ; i < arrayButtonsAchat.length ; i++)
			if(arrayButtonsAchat[i].clicked){
				arrayButtonsAchat[i].clicked = false;
				selectedAchat = arrayButtonsAchat[i];
				return arrayButtonsAchat[i];
			}
		return selectedAchat;
	}

	function piocheCassee(pioche){
		pioche.buttonMiner.disabled = true;
		pioche.buttonMiner.style.boxShadow = "0 0 0 white";
		pioche.buttonMiner.textContent = "Pioche cassée...";
		pioche.buttonMiner.style.fontSize = "1.2vw";
		pioche.buttonMiner.style.cursor = "default";
	}

	function piocheRepare(pioche){
		pioche.buttonMiner.disabled = false;
		pioche.buttonMiner.style.boxShadow = "0 0 20px white";
		pioche.buttonMiner.textContent = "Miner";
		pioche.buttonMiner.style.fontSize = "1.6vw";
		pioche.buttonMiner.style.cursor = "pointer";
	}

	function toArray(array){
		let tab = [];
		for(let i = 0 ; i < array.length ; i++)
			tab.push(array[i]);
		return tab;
	}

	function spoilerAlerte(){
		nbAttaques += 1;
		var pioche = arrayPioches[Math.floor(Math.random()*Math.floor(arrayPioches.length-1))];

		var alerte = document.createElement("div");
		alerte.setAttribute("class","popUp");
		alerte.style.display = "flex";
		alerte.style.borderRadius = "50px";
		let temp = selectedOnglet;
		selectOnglet(0);
		selectedOnglet = temp;
		document.body.insertBefore(alerte, document.getElementById("gameover"));
		alerte.style.left = pioche.offsetLeft + pioche.offsetWidth/2 - alerte.offsetWidth/2 + "px";
		alerte.style.top = pioche.offsetTop + pioche.offsetHeight/2 - alerte.offsetHeight/2 + "px";
		alerte.remove();
		if(selectedOnglet != 0 && nbAttaques > 1) selectOnglet(selectedOnglet);
		var tpsRestant = 10;
		alerte.textContent = tpsRestant;
		document.body.insertBefore(alerte, document.getElementById("gameover"));

		var id = setInterval(clignoter, 500);
		function clignoter(){
			if(arrayPioches.length <= 0){
				clearInterval(id);
				alerte.remove();
			}
			else if(tpsRestant >= -1){
				if(alerte.style.display == "none"){
					alerte.style.display = "flex";
					tpsRestant -= 1;
				}
				else
					alerte.style.display = "none";
				if(tpsRestant == -1 && alerte.style.display == "flex"){
					alerte.style.display = "none";
					tpsRestant = 11;
					clearInterval(id);
					if(arrayPioches.length > 0) lancerUneAttaque(alerte, pioche);
				}
				alerte.textContent = "" + tpsRestant + "";
			}
		}
	}

	function lancerUneAttaque(alerte, pioche){
		let bombe = document.createElement("img");
		bombe.setAttribute("src","images/bombe.png");
		bombe.setAttribute("alt","BOMBE");
		bombe.setAttribute("class","bombe");
		bombe.addEventListener("mousedown",function(){
			this.style.display = "none";
			arrayInv[0] += 50;
			updateGold();
			alerte.remove();
			nbAttaques -= 1;
		});
		bombe.style.animationName = "tourne";
		document.getElementsByTagName("section")[0].appendChild(bombe);
		avancerBombe(bombe, pioche, alerte);
	}

	function avancerBombe(bombe, pioche, alerte){
		var rand = Math.floor(Math.random()*Math.floor(document.body.offsetWidth));
		var targetWidth = pioche.offsetLeft + pioche.offsetWidth/2 - rand;
		var targetTop = pioche.offsetTop + pioche.offsetHeight/2;
		let distance = Math.sqrt(targetWidth*targetWidth+targetTop*targetTop);
		while(distance <= document.body.offsetWidth*0.25){
			rand = Math.floor(Math.random()*Math.floor(document.body.offsetWidth));
			targetWidth = pioche.offsetLeft + pioche.offsetWidth/2 - rand;
			targetTop = pioche.offsetTop + pioche.offsetHeight/2;
			distance = Math.sqrt(targetWidth*targetWidth+targetTop*targetTop);
		}
		var inverse = false;
		if(rand > pioche.offsetLeft + pioche.offsetWidth/2){
			inverse = true;
			targetWidth = rand - pioche.offsetLeft - pioche.offsetWidth/2;
		}
		var posLeft = rand;
		var posTop = 0;
		var midLeft = posLeft + bombe.offsetWidth/2;
		var normal = true;
		if(targetWidth < targetTop)
			normal = false;
		let p = 5;
		if(document.body.offsetWidth < 1200)
			p = 12;
		else if(document.body.offsetWidth < 900)
			p = 20;
		let vitesse = Math.ceil(distance/(document.body.offsetWidth/p));
		var id = setInterval(frame, vitesse);
		function frame(){
			if(Math.abs(posLeft+bombe.offsetWidth/2-rand) >= targetWidth && Math.abs(posTop+bombe.offsetHeight/2) >= targetTop && bombe.style.display != "none"){
				clearInterval(id);
				bombe.style.animationName = "";
				bombe.style.display = "none";
				alerte.remove();
				nbAttaques -= 1;
				arrayValDepart[4] -= 5;
				destroy(pioche);
			}
			else if(bombe.style.display == "none" || arrayPioches.length <= 0){
				clearInterval(id);
				bombe.style.display = "none";
			}
			else{
				if(normal){
					if(!inverse){
						posTop += 1;
						posLeft += targetWidth/targetTop;
					}
					else{
						posTop += 1;
						posLeft -= targetWidth/targetTop;
					}
				}
				else{
					if(!inverse){
						posLeft += 1;
					}
					else{
						posLeft -= 1;
					}
					posTop += targetTop/targetWidth;
				}
				bombe.style.top = posTop + "px";
				bombe.style.left = posLeft + "px";
			}
		}
	}

	function destroy(pioche){
		pioche.style.display = "none";
		updateArraysSupp(pioche);
		if(arrayPioches.length <= 0 && arrayInv[0] < arrayPrix[6])
			gameOver();
	}

	function updateArraysSupp(pioche){
		arrayPioches.splice(arrayPioches.indexOf(pioche), 1);
		arrayButtonsChoisir.splice(arrayPioches.indexOf(pioche.buttonChoisir), 1);
		arrayValsMineAffiche.splice(arrayPioches.indexOf(pioche.getElementsByClassName("stuff")[0]), 1);
		arrayValsMineAffiche.splice(arrayPioches.indexOf(pioche.getElementsByClassName("stuff")[1]), 1);
		arrayValsMineAffiche.splice(arrayPioches.indexOf(pioche.getElementsByClassName("stuff")[2]), 1);
	}

	function gameOver(){
		document.getElementById("gameover").style.display = "flex";
	}
}