import tkinter
import time
import random

class variables():

	def init(self):
		self.test_taille = 1
		self.largeur = 0
		self.hauteur = 0
		self.f_menu = 0
		self.colonne_action = 0
		self.memoire_colonne = 0
		self.pala = 10
		self.pala2 = 10
		self.pala_spell2 = 0
		self.tour = 0
		self.case = 0
		self.timer = 0
		self.mem_tour = 0
		self.mem_tour2 = 0
		self.demande = 0
		self.l1 = []
		self.l2 = []
		self.l3 = []
		self.l4 = []
		self.l5 = []
		self.l6 = []
		self.grille = []
		self.spell1_j1 = 0
		self.spell2_j1 = 0
		self.spell3_j1 = 0
		self.spell1_j2 = 0
		self.spell2_j2 = 0
		self.spell3_j2 = 0
		self.canevas = 0
		self.fenetre = 0
		self.bouton_retour_w = 0
		self.Jn = 0
		self.coup_feuille = 0
		self.feuille = 0
		self.img_special = 0
		self.bouton_special_w = 0
		self.bouton_special_w2 = 0
		self.bouton_special_w3 = 0
		self.done = 0
		self.done2 = 0
		self.info = 0
		self.details_w = 0
		self.img2 = 0
		self.stop = 0

var = variables()

######################################################################## TEST SI LE PION POSE ENTRAINE UN PUISSANCE 4 ##############################################################################################

def test_win():
	"""
	Fonction qui regarde si le tableau possède un puissance 4
	"""
	compteurj1h = 0
	compteurj2h = 0
	compteurj1v = 1
	compteurj2v = 1
	compteurj1d = 1
	compteurj2d = 1
	ligne = 5-var.case
	nb_pion_av_gauche = 0
	nb_pion_av_droite = 0
	coord_pion_x = 0
	droite = 0
	gauche = 0
	breakk = 0 # Lorsqu'un puissance 4 a été détécté, pour pas faire d'autres calculs, on met au début de chaque test une petite condition

#################### TEST HORIZONTAL ####################
	c = -1
	for j in var.grille[ligne]: # On regarde si il y a 4 pions alignés horizontalement sur la ligne où le pion a été posé
		c += 1
		if j == 1:
			compteurj1h += 1
		elif j == 2:
			compteurj2h += 1
		if compteurj1h == 4 or compteurj2h == 4:
			coord_pion_x = c-3 # Coordonnées du pion en abscisse pour la mise en évidence tu puissance 4
			breakk += 1
			break
		if compteurj1h > 0 and j == 2 or j == 0:
			compteurj1h = 0
		if compteurj2h > 0 and j == 1 or j == 0:
			compteurj2h = 0

#################### TEST VERTICALES ####################
	if breakk == 0:  
		for j in range(0,3):
			for k in range(0,7):
				if var.grille[j][k] == var.Jn and var.grille[j+1][k] == var.Jn and var.grille[j+2][k] == var.Jn and var.grille[j+3][k] == var.Jn:
					if var.Jn == 1:
						compteurj1v = 4
					elif var.Jn == 2:
						compteurj2v = 4
	
#################### TEST DIAGONALES ####################
    # On regarde pour chacunes des deux diagonales différentes, le nombre de pions avant et après le pion posé.
    # Si le total des pions avant et après vaut 3, alors il y a un puissance 4
	if breakk == 0:  
		for t in range(1,4):                                                    #---------------------------------------------------------------------------------
			if ligne+t <= 5 and var.colonne_action+t <= 6:
				if var.Jn == var.grille[ligne+t][var.colonne_action+t]:
					if var.Jn == 1:
						compteurj1d += 1
					elif var.Jn == 2:                                                            
						compteurj2d += 1                                            #       Sens nord-ouest -> sud-est
					if compteurj1d == 4 or compteurj2d == 4:
						breakk += 1
						gauche = 1
						break
				else:
					break
			else:
				break   
	if breakk == 0:                                                                                      #               DIAGONALE DIRECTION NORD-OUEST / SUD-EST
		for k in range(1,4):
			if ligne-k >= 0 and var.colonne_action-k >= 0:
				if var.Jn == var.grille[ligne-k][var.colonne_action-k]:
					if var.Jn == 1:
						compteurj1d += 1
						nb_pion_av_gauche += 1 # Permet de savoir combien il y a de pion avant
					elif var.Jn == 2:               # le pion posé pour la mise en évidence du puissance 4                          
						compteurj2d += 1
						nb_pion_av_gauche += 1	
					if compteurj1d == 4 or compteurj2d == 4:                        #       Sens sud-est -> nord-ouest
						breakk += 1
						gauche = 1
						break
				else:
					compteurj1d = 1
					compteurj2d = 1
					break                                                                   
			else:
				compteurj1d = 1
				compteurj2d = 1
				break                                                           #---------------------------------------------------------------------------------
	
	if breakk == 0:                                                             #---------------------------------------------------------------------------------
		for i in range(1,4):                                                                    
			if ligne+i <= 5 and var.colonne_action-i >= 0:
				if var.Jn == var.grille[ligne+i][var.colonne_action-i]:
					if var.Jn == 1:
						compteurj1d += 1
					elif var.Jn == 2:                                                    #       Sens nord-est -> sud-ouest
						compteurj2d += 1
					if compteurj1d == 4 or compteurj2d == 4:
						breakk += 1
						droite = 1
						break
				else:
					break
			else:                                                                                        #               DIAGONALE DIRECTION NORD-EST / SUD-OUEST 
				break    
	if breakk == 0:                                                                                                 
		for j in range(1,4):
			if ligne-j >= 0 and var.colonne_action+j <= 6:
				if var.Jn == var.grille[ligne-j][var.colonne_action+j]:
					if var.Jn == 1:
						compteurj1d += 1
						nb_pion_av_droite += 1
					elif var.Jn == 2:                                                    #       Sens sud-ouest -> nord-est
						compteurj2d += 1
						nb_pion_av_droite += 1
					if compteurj1d == 4 or compteurj2d == 4:
						droite = 1
						break
				else:
					compteurj1d = 1
					compteurj2d = 1
					break
			else:
				break                                                           #---------------------------------------------------------------------------------
	
	coul_trait = "#F2F1F0"
	if compteurj1h == 4 or compteurj1v == 4 or compteurj1d == 4 or compteurj2h == 4 or compteurj2v == 4 or compteurj2d == 4:
		
		### MISE EN EVIDENCE DU PUISSANCE 4 ###
		if compteurj1v == 4 or compteurj2v == 4:
			for k in range(0,4):
				var.canevas.create_text(o+var.colonne_action*carre_l+carre_l/2,o+(ligne+k)*carre_h+carre_h/2+18, text="*", anchor="center", fill=coul_trait, font=(police, 60))
		elif compteurj1h == 4 or compteurj2h == 4:
			for i in range(0,4):
				var.canevas.create_text(o+(coord_pion_x+i)*carre_l+carre_l/2,o+ligne*carre_h+carre_h/2+18, text="*", anchor="center", fill=coul_trait, font=(police, 60))
		elif compteurj1d == 4 or compteurj2d == 4:
			if gauche == 1:
				for j in range(0,4-nb_pion_av_gauche):
					var.canevas.create_text(o+(var.colonne_action+j)*carre_l+carre_l/2,o+(ligne+j)*carre_h+carre_h/2+18, text="*", anchor="center", fill=coul_trait, font=(police, 60))
				for l in range(1,nb_pion_av_gauche+1):
					var.canevas.create_text(o+(var.colonne_action-l)*carre_l+carre_l/2,o+(ligne-l)*carre_h+carre_h/2+18, text="*", anchor="center", fill=coul_trait, font=(police, 60))
			elif droite == 1:
				for m in range(0,4-nb_pion_av_droite):
					var.canevas.create_text(o+(var.colonne_action-m)*carre_l+carre_l/2,o+(ligne+m)*carre_h+carre_h/2+18, text="*", anchor="center", fill=coul_trait, font=(police, 60))
				for n in range(1,nb_pion_av_droite+1):
					var.canevas.create_text(o+(var.colonne_action+n)*carre_l+carre_l/2,o+(ligne-n)*carre_h+carre_h/2+18, text="*", anchor="center", fill=coul_trait, font=(police, 60))
		### FIN ###

		if var.Jn == 1:
			end(1)
		else:
			end(2)
	else:
		tableau_rempli()

################################################################### REMPLACEMEN TD'UNE CASE VIDE PAR UN PION DANS LA LISTE ###########################################################################################

def placer_pion():
	"""
	Fonction qui remplace dans la grille le 0 par un 1 ou 2 selon le joueur
	"""
	var.case = 0
	for i in range(5,-1,-1): # De bas en haut, il regarde si il y a un pion ou pas. Si non, il remplace le 0.
		if var.grille[i][var.colonne_action] == 0:
			var.grille[i][var.colonne_action] = var.Jn
			break
		else:
			var.case += 1
	var.tour += 1
	if var.Jn == 1:
		pion_qui_tombe("red")
	elif var.Jn == 2:
		pion_qui_tombe("#FFA500")
		
#################################################################################### TOUR DE L'ORDINATEUR ##########################################################################################################

def gagner_ou_bloquer(gagner, bloquer):
	"""
	Fonction qui regarde si l'ordinateur peut gagner, ou bloquer l'adversaire.
	Chaque test fonctionne ainsi : On test par groupe de 4 si on trouve 3 pions et 1 trou pour gagner ou pour bloquer.
	Sauf le test verticale puisque pour finir ou bloquer un puissance 3 vertical, le "trou" est toujours au dessus. Un simple boucle est donc réalisé.
	"""
	var.done = 0
	breakk = 0
	var.pala = 10 # La variable pala est là où l'ordinateur ne doit pas jouer. Si l'ordinateur joue dans une certaine colonne, et que ce pion
	var.pala2 = 10 # posé permet un puissance 4 à l'adversaire, pala est incrémenté de cette colonne "suicidaire". L'ordinateur a 2 "mémoire" pala

	### HORIZONTALE ###
	for j in range(0,6):
		if breakk == 1:
			break
		for k in range(0,4):
			if breakk == 1:
				break
			c = 0
			t = 0
			c_trou = 0 # Là où l'ordinateur doit jouer pour gagner, ou bloquer
			for m in range(k,k+4):
				if var.grille[j][m] == gagner:
					c += 1
				elif var.grille[j][m] == bloquer:
					break
				elif var.grille[j][m] == 0 and t == 1: # Si t = 1, et qu'on retrouve un deuxième trou, on break.
					break
				elif var.grille[j][m] == 0 and t == 0: # Si t = 1, cela veut dire qu'il y a un trou et c'est là qu'il faudra jouer.
					t = 1
					c_trou = m
				if c == 3 and t == 1:
					if j < 5:
						if var.grille[j][c_trou] == 0 and var.grille[j+1][c_trou] != 0:
							var.pala = 10
							var.pala2 = 10
							var.colonne_action = c_trou
							tableau_pseudo_rempli()
							if var.done == 0:
								var.colonne_action = c_trou
								colonne_remplie()
							var.done = 1
							breakk = 1
							break
						elif gagner == 1 and var.done == 0: # Si on est dans la phase de bloquage, c-à-d que l'ordinateur n'a pas pu gagner, on fait des tests pour voir où il ne doit pas jouer.
							if j < 4:
								if var.grille[j][c_trou] == 0 and var.grille[j+1][c_trou] == 0 and var.grille[j+2][c_trou] != 0:
									if var.pala == 10:
										var.pala = c_trou
									else:
										var.pala2 = c_trou
										if var.pala == var.pala2:
											var.pala2 == 10
							elif var.grille[j][c_trou] == 0 and var.grille[j+1][c_trou] == 0:
								if var.pala == 10:
									var.pala = c_trou
								else:
									var.pala2 = c_trou
									if var.pala == var.pala2:
										var.pala2 == 10
					elif j == 5:
						if var.grille[j][c_trou] == 0:
							var.pala = 10
							var.pala2 = 10
							var.colonne_action = c_trou
							tableau_pseudo_rempli()
							if var.done == 0:
								var.colonne_action = c_trou
								colonne_remplie()
							var.done = 1
							breakk = 1
							break

	### VERTICALE ###
	if var.done == 0:
		for j in range(0,4):
			if breakk == 1:
				break
			for k in range(0,7):
				if var.grille[j][k] == gagner and var.grille[j+1][k] == gagner and var.grille[j+2][k] == gagner and j > 0 and var.grille[j-1][k] == 0:
					var.pala = 10
					var.pala2 = 10
					var.colonne_action = k
					tableau_pseudo_rempli()
					if var.done == 0:
						var.colonne_action = k
						colonne_remplie()
					var.done = 1
					breakk = 1
					break

	### DIAGONALES GAUCHE A DROITE (haut/bas) ###
	if var.done == 0:
		breakk = 0
		for j in range(0,3):
			if breakk == 1:
				break
			for k in range(0,4):
				if breakk == 1:
					break
				c = 0
				t = 0
				l_trou = 0
				c_trou = 0
				n = 0
				for m in range(k,k+4):
					if var.grille[j+n][m] == gagner:
						c += 1
					elif var.grille[j+n][m] == bloquer:
						break
					elif var.grille[j+n][m] == 0 and t == 1:
						break
					elif var.grille[j+n][m] == 0 and t == 0:
						t = 1
						l_trou = j+n
						c_trou = m
					if c == 3 and t == 1:
						if l_trou < 5:
							if var.grille[l_trou][c_trou] == 0 and var.grille[l_trou+1][c_trou] != 0:
								var.pala = 10
								var.pala2 = 10
								var.colonne_action = c_trou
								tableau_pseudo_rempli()
								if var.done == 0:
									var.colonne_action = c_trou
									colonne_remplie()
								var.done = 1
								breakk = 1
								break
							elif gagner == 1 and var.done == 0: # Si on est dans la phase de bloquage, c-à-d que l'ordinateur n'a pas pu gagner, on fait des tests pour voir où il ne doit pas jouer.
								if l_trou < 4:
									if var.grille[l_trou][c_trou] == 0 and var.grille[l_trou+1][c_trou] == 0 and var.grille[l_trou+2][c_trou] != 0:
										if var.pala == 10:
											var.pala = c_trou
										else:
											var.pala2 = c_trou
											if var.pala == var.pala2:
												var.pala2 == 10
								elif var.grille[l_trou][c_trou] == 0 and var.grille[l_trou+1][c_trou] == 0:
									if var.pala == 10:
										pala = c_trou
									else:
										var.pala2 = c_trou
										if var.pala == var.pala2:
											var.pala2 == 10
						elif l_trou == 5:
							if var.grille[l_trou][c_trou] == 0:
								var.pala = 10
								var.pala2 = 10
								var.colonne_action = c_trou
								tableau_pseudo_rempli()
								if var.done == 0:
									var.colonne_action = c_trou
									colonne_remplie()
								var.done = 1
								breakk = 1
								break
					n += 1

	### DIAGONALES DROITE A GAUCHE (haut/bas) ###
	if var.done == 0:
		breakk = 0
		for j in range(5,2,-1):
			if breakk == 1:
				break
			for k in range(0,4):
				if breakk == 1:
					break
				c = 0
				t = 0
				l_trou = 0
				c_trou = 0
				n = 0
				for m in range(k,k+4):
					if var.grille[j-n][m] == gagner:
						c += 1
					elif var.grille[j-n][m] == bloquer:
						break
					elif var.grille[j-n][m] == 0 and t == 1:
						break
					elif var.grille[j-n][m] == 0 and t == 0:
						t = 1
						l_trou = j-n
						c_trou = m
					if c == 3 and t == 1:
						if l_trou < 5:
							if var.grille[l_trou][c_trou] == 0 and var.grille[l_trou+1][c_trou] != 0:
								var.pala = 10
								var.pala2 = 10
								var.colonne_action = c_trou
								tableau_pseudo_rempli()
								if var.done == 0:
									var.colonne_action = c_trou
									colonne_remplie()
								var.done = 1
								breakk = 1
								break
							elif gagner == 1 and var.done == 0: # Si on est dans la phase de bloquage, c-à-d que l'ordinateur n'a pas pu gagner, on fait des tests pour voir où il ne doit pas jouer.
								if l_trou < 4:
									if var.grille[l_trou][c_trou] == 0 and var.grille[l_trou+1][c_trou] == 0 and var.grille[l_trou+2][c_trou] != 0:
										if var.pala == 10:
											pala = c_trou
										else:
											var.pala2 = c_trou
											if var.pala == var.pala2:
												var.pala2 == 10
								elif var.grille[l_trou][c_trou] == 0 and var.grille[l_trou+1][c_trou] == 0:
									if var.pala == 10:
										var.pala = c_trou
									else:
										var.pala2 = c_trou
										if var.pala == var.pala2:
											var.pala2 == 10
						elif l_trou == 5:
							if var.grille[l_trou][c_trou] == 0:
								var.pala = 10
								var.pala2 = 10
								var.colonne_action = c_trou
								tableau_pseudo_rempli()
								if var.done == 0:
									var.colonne_action = c_trou
									colonne_remplie()
								var.done = 1
								breakk = 1
								break
					n += 1

def tableau_pseudo_rempli():
	"""
	Fonction qui test si l'ordinateur ne peut pas jouer dans la colonne où il a joué avant ou celle de droite ou celle de gauche.
	"""
	t = 0
	a = 0
	b = 0
	if var.colonne_action == 0:
		a = 0
		b = 1
	elif var.colonne_action == 7:
		a = 1
		b = 0
	else:
		a = 1
		b = 1
	for i in range(var.colonne_action-a,var.colonne_action+b+1):
		if var.l1[i] != 0 or i == var.pala or i == var.pala2:
			t += 1
		else:
			t = 0
			break
	if t == 3:
		colonne_restante()
		var.done = 1

def colonne_restante():
	"""
	Fonction qui fait jouer l'ordinateur là où il peut et non en fonction de son tour d'avant.
	"""
	t = 0
	for i in range(0,7):
		if var.l1[i] != 0 or i == var.pala or i == var.pala2:
			t += 1
		else:
			t = 0
			var.colonne_action = i
			placer_pion()
			break
	if t == 7:
		var.pala = 10
		var.pala2 = 10
		colonne_restante()

def tirage():
	"""
	Fonction qui permet d'incrémenter colonne_action, en fonction de là où l'ordinateur a joué au tour d'avant, aléatoirement.
	"""
	ok = 0
	while ok == 0:
		tirage = random.randint(1,21) # Système d'équilibrage : plus de chance de jouer à proximité du pion posé au tour précédent que de jouer plus loin.
		if tirage <= 3:
			var.colonne_action = var.memoire_colonne
		elif tirage > 3 and tirage <= 8:
			if var.memoire_colonne-1 > 0:
				ok = 1
				var.colonne_action = var.memoire_colonne-1
		elif tirage > 8 and tirage <= 13:
			if var.memoire_colonne+1 < 6:
				ok = 1
				var.colonne_action = var.memoire_colonne+1
		elif tirage == 14 or tirage == 15:
			if var.memoire_colonne-2 > 0:
				ok = 1
				var.colonne_action = var.memoire_colonne-2
		elif tirage == 16 or tirage == 17:
			if var.memoire_colonne+2 < 6:
				ok = 1
				var.colonne_action = var.memoire_colonne+2
		elif tirage == 18 or tirage == 19:
			if var.memoire_colonne-3 > 0:
				ok = 1
				var.colonne_action = var.memoire_colonne-3
		elif tirage == 20 or tirage == 21:
			if var.memoire_colonne+3 < 6:
				ok = 1
				var.colonne_action = var.memoire_colonne+3

	return(var.colonne_action)

def ordinateur():
	"""
	C'est le coeur du fonctionnement d'un tour de jeu de l'ordinateur.
	"""
	var.pala = 10
	var.pala2 = 10
	if var.tour == 2: # Si c'est le premier tour de l'ordinateur, il va jouer complètement au hasard.
		var.colonne_action = random.randint(0,6)
		var.memoire_colonne = var.colonne_action
		placer_pion()
	else: # Sinon il regarde d'abord s'il peut : 
		gagner_ou_bloquer(2, 1) # Gagner
		if var.done == 0: # Lorsque le programme attend une action de l'utilisateur dans selection_colonne(), il remonte dans les fonctions. Le done signifie que le tour a été joué,
						  # donc on test avant de refaire chaque action, si done a été incrémenté pour pas jouer plusieurs fois en même temps.
			gagner_ou_bloquer(1, 2) # Ou alors bloquer

		if var.done == 0: # Ou sinon il joue aléatoirement en fonction de son pion posé précédement à l'aide de la fonction tirage()
			tableau_pseudo_rempli()
			if var.done == 0:
				var.colonne_action = tirage()
				var.memoire_colonne = var.colonne_action
				colonne_remplie()
	
################################################################################### FIN DE JEU ########################################################################################################################

def rejouer():
	var.fenetre.destroy()
	debut()

def quitter():
	var.fenetre.destroy()

def end(fin):
	"""
	Fonction qui change les informations à gauche et qui ajoute 2 boutons rejouer et quitter, lorsqu'un jouer a gagné
	"""
	information(fin)
	bouton_rejouer = tkinter.Button(var.fenetre, text="Rejouer", command= rejouer)
	bouton_quitter = tkinter.Button(var.fenetre, text="Quitter", command= quitter)
	bouton_rejouer_w = var.canevas.create_window(var.largeur-(var.largeur-(2*o+7*carre_l))/2,var.hauteur/2, window=bouton_rejouer)
	bouton_quitter_w = var.canevas.create_window(var.largeur-(var.largeur-(2*o+7*carre_l))/2,var.hauteur/2+85, window=bouton_quitter)
			
###################################################################### KEY BINDING, ASSOCIATION D'UNE TOUCHE A UNE ACTION ############################################################################################

def souris(Clic):
	"""
	Fonction qui récupère l'endroit où a cliqué l'utilisateur, pour ainsi savoir sur quelle colonne le joueur a cliqué
	"""
	if var.stop == 0:
		if Clic.x < o or o+7*carre_l < Clic.x:
			if var.tour == 1:
				var.tour = 3
			selection_colonne()
		else:
			for i in range(0,7):
				if o+carre_l*i < Clic.x and Clic.x < o+carre_l*(i+1):
					var.colonne_action = i
					if var.spell2_j1 == 1 or var.spell2_j2 == 1: # Le joueur n'a pas le droit de jouer là où il a fait disparaitre un pion (voir sort disparition)
						if var.colonne_action == var.pala_spell2: 
							selection_colonne()
							break
					var.pala_spell2 = 10
					colonne_remplie()

def clavier(event):
	"""
	Fonction qui récupère la colonne choisit par le joueur avec le clavier
	"""
	if var.stop == 0:
		var.colonne_action = int(event.char)-1
		if var.spell2_j1 == 1 or var.spell2_j2 == 1: # Le joueur n'a pas le droit de jouer là où il a fait disparaitre un pion (voir sort Disparition)
			if var.colonne_action == var.pala_spell2: 
				selection_colonne()
		else:
			var.pala_spell2 = 10
			colonne_remplie()

def selection_colonne():
	"""
	Fonction qui attent que l'utilisateur choisisse une colonne en cliquant avec la souris ou le clavier
	"""
	var.colonne_action = 0
	if var.stop == 0:
		information(0)
	if var.demande == 1 and var.Jn == 2 and var.stop == 0:
		ordinateur()
	else:
		if var.timer == 1 and var.tour == var.mem_tour2+1: # Si un jouer a activé le sort Timer (voir sort Timer)
			selection_colonne_avec_temps()
		else:
			var.canevas.bind('<Button-1>', souris)
			var.canevas.bind_all('<Key-1>', clavier)
			var.canevas.bind_all('<Key-2>', clavier)
			var.canevas.bind_all('<Key-3>', clavier)
			var.canevas.bind_all('<Key-4>', clavier)
			var.canevas.bind_all('<Key-5>', clavier)
			var.canevas.bind_all('<Key-6>', clavier)
			var.canevas.bind_all('<Key-7>', clavier)

################################################################### TEST SI LE TABLEAU OU LA COLONNE CHOISIT EST REMPLIE ##########################################################################################

def colonne_remplie():
	"""
	Fonction qui test si la colonne est remplie ou si l'ordinateur ne doit pas jouer sur pala ou pala2
	"""
	if var.l1[var.colonne_action] != 0:
		if var.demande == 1 and var.Jn == 2:
			ordinateur()
		else:
			selection_colonne()
			
	elif var.demande == 1 and var.Jn == 2:
		if var.colonne_action == var.pala or var.colonne_action == var.pala2:
			ordinateur()
		else:
			placer_pion()
	else:
		placer_pion()

def tableau_rempli():
	"""
	Fonction qui test si le tableau est remplie
	"""
	remplie = 0
	for i in range(0,7):
		if var.l1[i] != 0:
			remplie += 1
		else:
			remplie = 0
	if remplie == 7:
		end(3)
	else:
		remplie = 0

########################################################################################### MENU ET DEBUT ##########################################################################################################

def menu():
	"""
	C'est le menu. C'est là où l'utilisateur choisit si il veut jouer en 1 contre 1 avec un ami ou s'il choisit de joeur contre l'ordinateur, ou alors quitter
	"""
	largeur_menu = 500
	hauteur_menu = 400
	var.f_menu = tkinter.Tk()
	var.f_menu.geometry('{}x{}+{}+{}'.format(largeur_menu,hauteur_menu, position_l,position_h))
	var.f_menu.title("Menu")
	c_menu = tkinter.Canvas(var.f_menu, width = largeur_menu, height = hauteur_menu)
	img = tkinter.PhotoImage(file = "menu2.gif")
	c_menu.create_image(0,0, image=img, anchor="nw")
	c_menu.pack()

	cmoi = c_menu.create_text(330,175, text="Que souhaites-tu faire ?", fill="red", anchor="center", font=(police, 17))

	b_1v1 = tkinter.Button(var.f_menu, text="Je défie un ami !", command= duo)
	b_1v1_w = c_menu.create_window(330,230, window=b_1v1)
	b_1vpc = tkinter.Button(var.f_menu, text="Je défie l'ordinateur !", command= solo)
	b_1vpc_w = c_menu.create_window(330,285, window=b_1vpc)
	b_quitter = tkinter.Button(var.f_menu, text="Quitter", command= exit)
	b_quitter_w = c_menu.create_window(330,340, window=b_quitter)
	
	salut = c_menu.create_text(80,230, text="Choisis la taille :", fill="red", anchor="center", font=(police, 12))

	petite_taille = tkinter.Button(var.f_menu, text="Petite", command= taille1)
	petite_taille_w = c_menu.create_window(80,270, window=petite_taille)
	moyenne_taille = tkinter.Button(var.f_menu, text="Moyenne", command= taille2)
	moyenne_taille_w = c_menu.create_window(80,310, window=moyenne_taille)
	grande_taille = tkinter.Button(var.f_menu, text="Grande", command= taille3)
	grande_taille_w = c_menu.create_window(80,350, window=grande_taille)

	var.f_menu.mainloop()


def taille1():
	var.largeur = 1000
	var.hauteur = 400

def taille2():
	var.largeur = 1250
	var.hauteur = 500

def taille3():
	var.largeur = 1500
	var.hauteur = 600

def duo():
	var.demande = 2
	var.f_menu.destroy()

def solo():
	var.demande = 1
	var.f_menu.destroy()

def exit():
	var.demande = 0
	var.f_menu.destroy()

def debut():
	"""
	Toute première fonction d'une partie. Là où les variables sont remise à zéro
	"""
	global o
	global r
	global carre_h
	global carre_l
	global taille
	var.l1 = [0,0,0,0,0,0,0]
	var.l2 = [0,0,0,0,0,0,0]
	var.l3 = [0,0,0,0,0,0,0]
	var.l4 = [0,0,0,0,0,0,0]
	var.l5 = [0,0,0,0,0,0,0]
	var.l6 = [0,0,0,0,0,0,0]
	var.grille = [var.l1,var.l2,var.l3,var.l4,var.l5,var.l6]
	var.tour = 1
	var.Jn = 1
	var.test_taille = 1
	var.stop = 0
	var.spell1_j1 = 0
	var.spell2_j1 = 0
	var.spell3_j1 = 0
	var.spell1_j2 = 0
	var.spell2_j2 = 0
	var.spell3_j2 = 0
	var.timer = 0
	var.mem_tour = 100
	var.mem_tour2 = 100
	var.coup_feuille = 0
	var.demande = 0
	menu()
	taille = int(var.largeur/50)
	o = var.largeur/40
	r = var.largeur/200
	carre_h = int((var.hauteur-var.largeur/50-o)/6)
	carre_l = int((var.largeur-var.largeur/2-o)/7)

	if var.demande != 0:
		var.fenetre = tkinter.Tk()
		var.fenetre.geometry('{}x{}+{}+{}'.format(var.largeur,var.hauteur, position_l,position_h))
		var.fenetre.title("Puissance 4")
		if var.demande == 2:
			if var.largeur == 1000:
				img1 = tkinter.PhotoImage(fil="fond_d2.gif")
			elif var.largeur == 1250:
				img1 = tkinter.PhotoImage(fil="fond_d2_m.gif")
			else:
				img1 = tkinter.PhotoImage(fil="fond_d2_g.gif")
		else:
			if var.largeur == 1000:
				img1 = tkinter.PhotoImage(fil="fond_d1.gif")
			elif var.largeur == 1250:
				img1 = tkinter.PhotoImage(fil="fond_d1_m.gif")
			else:
				img1 = tkinter.PhotoImage(fil="fond_d1_g.gif")
		var.canevas = tkinter.Canvas(var.fenetre, width = var.largeur, height = var.hauteur)
		var.canevas.create_image(0,0, image=img1, anchor="nw")
		bouton_retour = tkinter.Button(var.fenetre, text="Retour", command= retour)
		var.bouton_retour_w = var.canevas.create_window(var.largeur-20, 20, anchor="ne", window= bouton_retour)
		var.canevas.pack()

		creer_grille()
		selection_colonne()
				
		var.fenetre.mainloop()

def retour():
	var.fenetre.destroy()
	debut()

################################################################### CREATION DE LA GRILLE ET DES PIONS AVEC LEUR ANIMATION #########################################################################################

def creer_grille():
	"""
	Fonction qui s'occupe de l'affichage. Elle crée le plateau en créant un gros carré bleu, puis à l'aide
	d'une boucle, des cercles blanc pour simuler des trous, et enfin 2 cercles en bas à gauche et en
	bas à droite pour faire un semblant de socle.
	"""
	for k in range(1,8):
		var.canevas.create_text(o+carre_l*(k-0.5),o/2, text="{}".format(k), anchor="center", fill="black", font=(police, 12))
	var.canevas.create_rectangle(o,o, o+carre_l*7,o+carre_h*6+o, fill="blue", outline="blue")
	for i in range(0,6):
		for k in range(0,7):
			var.canevas.create_oval(o+r+carre_l*k+((carre_l-carre_h)/2),o+r+carre_h*i, o-((carre_l-carre_h)/2)-r+carre_l*(k+1),o-r+carre_h*(i+1), fill="#F2F1F0", outline="blue")

	var.canevas.create_oval(var.hauteur/220,o+carre_h*6-var.largeur/95, var.hauteur/220+carre_l,o+carre_h*7-var.largeur/95, fill="blue", outline="blue")
	var.canevas.create_oval(o*2+carre_l*7-var.hauteur/220,o+carre_h*6-var.largeur/95, o*2+carre_l*6-var.hauteur/220,o+carre_h*7-var.largeur/95, fill="blue", outline="blue")


def pion_qui_tombe(coul_pion):
	"""
	Fonction qui créer un pion puis l'animation du pion qui tombe
	"""
	if var.coup_feuille == 1: # Si le joueur a lancé le sort Aveuglement (voir sort Aveuglement)
		aveuglement_up()
		var.coup_feuille = 0
	changement() # Si on spam, sans le changement, on poserait que des pions de la même couleur
	pion = var.canevas.create_oval(o+((carre_l-carre_h)/2)+var.colonne_action*carre_l+r,-carre_h+r, o-((carre_l-carre_h)/2)+(var.colonne_action+1)*carre_l-r,-r, fill=coul_pion, outline=coul_pion)
	for i in range(0,int(((6-var.case)*carre_h+o)/(var.hauteur/200))):
		var.canevas.move(pion, 0,var.hauteur/200)
		var.fenetre.update()
		if var.mem_tour != 100: # Si le sort Aveuglement a été lancé, il faut gardé le pion derrière l'image de la feuille
			var.canevas.tag_raise(var.feuille) # On replace donc l'image par dessus
		time.sleep(0.004)
	changement() # On remet le véritable tour du joueur pour tester si il a gagné ou non
	test_win()
	changement() # C'est désormais au tour de l'autre joueur
	if var.tour == var.mem_tour+1:
		aveuglement_down()
	selection_colonne()

def changement():
	if var.Jn == 1:
		var.Jn += 1
	elif var.Jn == 2:
		var.Jn -= 1

###################################################################################### SORTS ################################################################################################################

### Sort 1, Aveuglement ###
def aveuglement():
	"""
	Fonction qui incrémente coup_feuille de 1. coup_feuille est la variable qui dit qu'un joueur a activé le sort Aveuglement
	"""
	var.coup_feuille = 1
	if var.Jn == 1:
		var.spell1_j1 = 1
	else:
		var.spell1_j2 = 1
	var.canevas.delete(var.bouton_special_w)

def aveuglement_up():
	var.mem_tour = var.tour
	if var.largeur == 1000:
		var.img_special = tkinter.PhotoImage(file="feuille.gif")
	elif var.largeur == 1250:
		var.img_special = tkinter.PhotoImage(file="feuille_m.gif")
	else:
		var.img_special = tkinter.PhotoImage(file="feuille_g.gif")
	var.feuille = var.canevas.create_image(0,0, image=var.img_special, anchor="nw")

def aveuglement_down():
	var.canevas.delete(var.feuille)
	var.mem_tour = 100

### Sort 2, Disparition ###
def del_pion():
	var.canevas.delete(var.bouton_special_w2)
	var.canevas.bind('<Button-1>', spell_souris)

def spell_souris(clic):
	"""
	Fonction qui fait disparaître un pion en haut d'une colonne choisit.
	La fonction récupère donc les coordonnées duclique de la souris, pour trouver la colonne,
	puis créer une cercle blanc et remplace dans la grille le 1 ou 2 par une case vide c-à-d un 0.
	"""
	if clic.x < o or o+7*carre_l < clic.x:
		del_pion()
	else:
		for i in range(0,7):
			if o+carre_l*i < clic.x and clic.x < o+carre_l*(i+1):
				for k in range(5,-1,-1):
					if var.grille[k][i] == 0:
						if k == 5:
							var.pala_spell2 = 10
							break
						else:
							j = k+1
							var.grille[j][i] = 0
							var.canevas.create_oval(o+((carre_l-carre_h)/2)+i*carre_l+r,o+j*carre_h+r, o-((carre_l-carre_h)/2)+(i+1)*carre_l-r,o+(j+1)*carre_h-r, fill="#F2F1F0", outline="blue")
							var.pala_spell2 = i
							break
					elif k == 0:
						if var.grille[k][i] != 0:
							j = k
							var.grille[j][i] = 0
							var.canevas.create_oval(o+((carre_l-carre_h)/2)+i*carre_l+r,o+j*carre_h+r, o-((carre_l-carre_h)/2)+(i+1)*carre_l-r,o+(j+1)*carre_h-r, fill="#F2F1F0", outline="blue")
							var.pala_spell2 = i
							break
				if var.Jn == 1:
					var.spell2_j1 = 1
				else:
					var.spell2_j2 = 1
				if var.tour == 1:
					var.tour = 3
				selection_colonne()

### Sort 3, timer ###
def timer_spell():
	"""
	Fonction qui incrémente timer de 1. timer est la variable qui dit qu'un joueur a activé le sort Timer
	"""
	var.mem_tour2 = var.tour
	var.timer = 1
	if var.Jn == 1:
		var.spell3_j1 = 1
	else:
		var.spell3_j2 = 1
	var.canevas.delete(var.bouton_special_w3)

def ok_s(event):
	"""
	Fonction qui récupère l'endroit où a cliqué l'utilisateur, pour ainsi savoir sur quelle colonne le joueur a cliqué
	"""
	if event.x < o or o+7*carre_l < event.x:
		pass
	else:
		for i in range(0,7):
			if o+carre_l*i < event.x and event.x < o+carre_l*(i+1):
				var.done2 = i
				var.colonne_action = var.done2
				colonne_remplie()

def ok_c(event):
	"""
	Fonction qui récupère la colonne choisit par le joueur avec le clavier
	"""
	var.done2 = int(event.char)-1
	var.colonne_action = var.done2
	colonne_remplie()

def selection_colonne_avec_temps():
	"""
	Meme fonction que selection_colonne(), seulement, si après 2 secondes le joueur n'a pas joué, cela passe au joueur suivant
	"""
	var.done2 = 10
	var.canevas.bind('<Button-1>', ok_s)
	var.canevas.bind_all('<Key-1>', ok_c)
	var.canevas.bind_all('<Key-2>', ok_c)
	var.canevas.bind_all('<Key-3>', ok_c)
	var.canevas.bind_all('<Key-4>', ok_c)
	var.canevas.bind_all('<Key-5>', ok_c)
	var.canevas.bind_all('<Key-6>', ok_c)
	var.canevas.bind_all('<Key-7>', ok_c)
	var.canevas.after(2000, apres)

def apres():
	"""
	Fonction qui passe au tour suivant
	"""
	if var.done2 == 10:
		var.timer = 0
		if var.mem_tour != 100:
			aveuglement_down()
			var.mem_tour = 100
		changement()
		selection_colonne()

### Autres ###
def details_sorts():
	"""
	Fonction qui créer une fenêtre qui détaille comment marchent les sorts
	"""
	var.fenetre_sorts = tkinter.Toplevel()
	var.fenetre_sorts.geometry('{}x{}+{}+{}'.format(var.largeur,int(var.hauteur/2), position_l,position_h-int(var.hauteur/2)))
	var.fenetre_sorts.title("Détails des sorts")
	img_detail = tkinter.PhotoImage(file = "fond_detail.gif")
	can_detail = tkinter.Canvas(var.fenetre_sorts, width = var.largeur, height = int(var.hauteur/2))
	can_detail.create_image(0,0, image=img_detail, anchor="nw")
	can_detail.pack()
	var.fenetre_sorts.mainloop()

################################################################################### INFORMATIONS ############################################################################################################

def information(gagne):
	"""
	Fonction qui change les boutons des sorts, le texte. Elle s'occupe des informations situé à droite du plateau
	"""
	if var.tour == 1 and var.demande == 2:
		details = tkinter.Button(var.fenetre, text="Détails des sorts", bg="#009900", command= details_sorts)
		var.details_w = var.canevas.create_window(var.largeur-(var.largeur-(2*o+7*carre_l))/2+0.7*var.hauteur/8,var.hauteur-50, anchor="w", window= details)
	if gagne == 0:
		if var.demande == 1 and var.Jn == 2:
			text = "Ordinateur"
		else:
			text = "Joueur {}".format(var.Jn)
		if var.Jn == 1:
			### On supprime les anciennes info et boutons ###
			if var.tour != 1:
				var.canevas.delete(var.info)
				if var.demande == 2:
					var.canevas.delete(var.bouton_special_w)
					var.canevas.delete(var.bouton_special_w2)
					var.canevas.delete(var.bouton_special_w3)
			### On crée les nouveaux ###
			var.info = var.canevas.create_text(var.largeur-(var.largeur-(2*o+7*carre_l))/2,var.hauteur/4-12, text=text, fill="red", anchor="center", font=(police, taille))
			if var.demande == 2:
				if var.spell1_j1 == 0:
					bouton_special = tkinter.Button(var.fenetre, text="Aveuglement !", bg="#ffb3b3", command= aveuglement) 
					var.bouton_special_w = var.canevas.create_window(var.largeur-(var.largeur-(2*o+7*carre_l))+var.largeur/10,var.hauteur/3+var.largeur/100, window=bouton_special)
				if var.spell2_j1 == 0:
					bouton_special2 = tkinter.Button(var.fenetre, text="Disparition !", bg="#ffb3b3", command= del_pion) 
					var.bouton_special_w2 = var.canevas.create_window(var.largeur-(var.largeur-(2*o+7*carre_l))+1.04*var.largeur/4,var.hauteur/3+var.largeur/100, window=bouton_special2)
				if var.spell3_j1 == 0:
					bouton_special3 = tkinter.Button(var.fenetre, text="Timer !", bg="#ffb3b3", command= timer_spell) 
					var.bouton_special_w3 = var.canevas.create_window(var.largeur-(var.largeur-(2*o+7*carre_l))+var.hauteur,var.hauteur/3+var.largeur/100, window=bouton_special3)
		elif var.Jn == 2:
			### On supprime les anciens ###
			var.canevas.delete(var.info)
			var.info = var.canevas.create_text(var.largeur-(var.largeur-(2*o+7*carre_l))/2,var.hauteur/4-12, text=text, fill="#FFA500", anchor="center", font=(police, taille))
			if var.demande == 2:
				var.canevas.delete(var.bouton_special_w)
				var.canevas.delete(var.bouton_special_w2)
				var.canevas.delete(var.bouton_special_w3)
				### On crée les nouveaux ###
				if var.spell1_j2 == 0:
					bouton_special = tkinter.Button(var.fenetre, text="Aveuglement !", bg="#ffe4b3", command= aveuglement) 
					var.bouton_special_w = var.canevas.create_window(var.largeur-(var.largeur-(2*o+7*carre_l))+var.largeur/10,var.hauteur/3+var.largeur/100, window=bouton_special)
				if var.spell2_j2 == 0:
					bouton_special2 = tkinter.Button(var.fenetre, text="Disparition !", bg="#ffe4b3", command= del_pion) 
					var.bouton_special_w2 = var.canevas.create_window(var.largeur-(var.largeur-(2*o+7*carre_l))+var.largeur*1.04/4,var.hauteur/3+var.largeur/100, window=bouton_special2)
				if var.spell3_j2 == 0:
					bouton_special3 = tkinter.Button(var.fenetre, text="Timer !", bg="#ffe4b3", command= timer_spell) 
					var.bouton_special_w3 = var.canevas.create_window(var.largeur-(var.largeur-(2*o+7*carre_l))+var.hauteur,var.hauteur/3+var.largeur/100, window=bouton_special3)
				if var.mem_tour != 100:
					var.canevas.tag_raise(var.feuille)
		
	else: # Lorsqu'un joueur a gagné, on supprime les anciens boutons, on recréer une image par dessus comme si on effaçait tout, puis on crée les nouvelles info
		var.canevas.delete(var.bouton_retour_w)
		if var.demande == 2:
			var.canevas.delete(var.details_w)
			var.canevas.delete(var.bouton_special_w)
			var.canevas.delete(var.bouton_special_w2)
			var.canevas.delete(var.bouton_special_w3)
		if var.largeur == 1000:
			var.img2 = tkinter.PhotoImage(file = "fond_end.gif")
		elif var.largeur == 1250:
			var.img2 = tkinter.PhotoImage(file = "fond_end_m.gif")
		else:
			var.img2 = tkinter.PhotoImage(file = "fond_end_g.gif")
		var.canevas.create_image(var.largeur,0, image=var.img2, anchor="ne")
		if var.demande == 1 and gagne == 2:
			text = "Ordinateur a gagné"
			coul = "#FFA500"
		elif gagne == 1:
			text = "Joueur 1 a gagné"
			coul = "red"
		elif gagne == 2:
			text = "Joueur 2 a gagné"
			coul = "#FFA500"
		else:
			text = "Egalité !"
			coul = "black"
		var.canevas.create_text(var.largeur-(var.largeur-(2*o+7*carre_l))/2,var.hauteur/4-12, text=text, fill=coul, anchor="center", font=(police, taille))
		var.stop = 1

######################################################################################### MAIN LOOP ################################################################################################################

var.largeur = 1000
var.hauteur = 400
position_l = 150
position_h = 150
police = "Coolsville"

debut()
