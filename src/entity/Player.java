package entity;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import main.GamePanel;
import main.KeyHandler;
import object.OBJ_Armor_Silver;
import object.OBJ_Fireball;
import object.OBJ_Helmet_Silver;
import object.OBJ_Key;
import object.OBJ_Rock;
import object.OBJ_Shield_Wood;
import object.OBJ_Sword_Normal;
import monster.MON_GreenSlime;

public class Player extends Entity{
	
	KeyHandler keyH;
	
	public final int screenX;
	public final int screenY;
//	public int hasKey = 0;
	int standCounter = 0;
	public boolean attackCanceled = false;
	public ArrayList<Entity> inventory = new ArrayList<>();
	public final int maxInventorySize = 20;
	public int color;

	
	public Player(GamePanel gp, KeyHandler keyH) {
		
		super(gp);
		this.keyH = keyH;
		screenX = gp.screenWidth/2 - (gp.tileSize/2);
		screenY = gp.screenHeight/2 - (gp.tileSize/2);
		
		solidArea = new Rectangle();
		solidArea.x = 8;
		solidArea.y = 16;
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;
		solidArea.width = 32;
		solidArea.height = 32;
//		
//		attackArea.width = 36;
//		attackArea.height = 36;
		
		getPlayerImage();
		setDefaultValues();
		getPlayerAttackImage();
		setItems();
	}
	
	public void setDefaultValues() {
		worldX = gp.tileSize * 22;
		worldY = gp.tileSize *39;
//		worldX = gp.tileSize * 12;
//		worldY = gp.tileSize * 13;
		speed = 4;
		direction = "down";
		
		//player status
		level= 1;
		maxLife = 60;
		life = maxLife;
		maxMana = 4;
		mana = maxMana;
		ammo = 4;
		strength = 1;
		dexterity = 1;
		exp = 0;
		nextLevelExp = 5;
		currentWeapon = new OBJ_Sword_Normal(gp);
		currentShield = new OBJ_Shield_Wood(gp);
		currentArmor = new OBJ_Armor_Silver(gp);
		currentHelmet = new OBJ_Helmet_Silver(gp);
		projectile = new OBJ_Fireball(gp);
		attack = getAttact();
		defense = getDefense();
	}
	
	public void setDefaultPositions() {
		worldX = gp.tileSize * 22;
		worldY = gp.tileSize * 39;
		direction = "down";
	}
	
	public void restoreLifeAndMan() {
		life = maxLife;
		mana = maxMana;
		invincible = false;
	}
	
	public void setItems() {
		
		inventory.clear();
		inventory.add(currentWeapon);
		inventory.add(new OBJ_Key(gp));
	}
	
	public int getAttact() {
		attackArea = currentWeapon.attackArea;
		return attack = strength * currentWeapon.attackValue;
	}
	
	public int getDefense() {
		return defense = dexterity * (currentShield.defenseValue + currentArmor.defenseValue + currentHelmet.defenseValue);
	}
	
	public void getPlayerImage() {
		if(color == 0) {
			up1 = setup("/player/boy_up_1", gp.tileSize, gp.tileSize);
			up2 = setup("/player/boy_up_2", gp.tileSize, gp.tileSize);
			down1 = setup("/player/boy_down_1", gp.tileSize, gp.tileSize);
			down2 = setup("/player/boy_down_2", gp.tileSize, gp.tileSize);
			left1 = setup("/player/boy_left_1", gp.tileSize, gp.tileSize);
			left2 = setup("/player/boy_left_2", gp.tileSize, gp.tileSize);
			right1 = setup("/player/boy_right_1", gp.tileSize, gp.tileSize);
			right2 = setup("/player/boy_right_2", gp.tileSize, gp.tileSize);
		}
		if(color == 1) {
			up1 = setup("/npc/oldman_up_1", gp.tileSize, gp.tileSize);
			up2 = setup("/npc/oldman_up_2", gp.tileSize, gp.tileSize);
			down1 = setup("/npc/oldman_down_1", gp.tileSize, gp.tileSize);
			down2 = setup("/npc/oldman_down_2", gp.tileSize, gp.tileSize);
			left1 = setup("/npc/oldman_left_1", gp.tileSize, gp.tileSize);
			left2 = setup("/npc/oldman_left_2", gp.tileSize, gp.tileSize);
			right1 = setup("/npc/oldman_right_1", gp.tileSize, gp.tileSize);
			right2 = setup("/npc/oldman_right_2", gp.tileSize, gp.tileSize);
		}
		
		
	}

	public void getPlayerAttackImage() {
		if(currentWeapon.type == type_sword) {
			attackUp1 = setup("/player/boy_attack_up_1", gp.tileSize, gp.tileSize*2);
			attackUp2 = setup("/player/boy_attack_up_2", gp.tileSize, gp.tileSize*2);
			attackDown1 = setup("/player/boy_attack_down_1", gp.tileSize, gp.tileSize*2);
			attackDown2 = setup("/player/boy_attack_down_2", gp.tileSize, gp.tileSize*2);
			attackLeft1 = setup("/player/boy_attack_left_1", gp.tileSize*2, gp.tileSize);
			attackLeft2 = setup("/player/boy_attack_left_2", gp.tileSize*2, gp.tileSize);
			attackRight1 = setup("/player/boy_attack_right_1", gp.tileSize*2, gp.tileSize);
			attackRight2 = setup("/player/boy_attack_right_2", gp.tileSize*2, gp.tileSize);
		}
		
		if(currentWeapon.type == type_axe) {
			attackUp1 = setup("/player/boy_axe_up_1", gp.tileSize, gp.tileSize*2);
			attackUp2 = setup("/player/boy_axe_up_2", gp.tileSize, gp.tileSize*2);
			attackDown1 = setup("/player/boy_axe_down_1", gp.tileSize, gp.tileSize*2);
			attackDown2 = setup("/player/boy_axe_down_2", gp.tileSize, gp.tileSize*2);
			attackLeft1 = setup("/player/boy_axe_left_1", gp.tileSize*2, gp.tileSize);
			attackLeft2 = setup("/player/boy_axe_left_2", gp.tileSize*2, gp.tileSize);
			attackRight1 = setup("/player/boy_axe_right_1", gp.tileSize*2, gp.tileSize);
			attackRight2 = setup("/player/boy_axe_right_2", gp.tileSize*2, gp.tileSize);
		}
		
	}
	
	public void update() {
		
		if(attacking == true) {
			attacking();
		}
		else if(keyH.upPressed == true || keyH.downPressed == true || keyH.leftPressed == true || keyH.rightPressed == true || keyH.enterPressed == true) {
			
			if(keyH.upPressed == true) {
				direction = "up";
			} else if(keyH.downPressed == true) {
				direction = "down";
			} else if(keyH.leftPressed == true) {
				direction = "left";
			} else if(keyH.rightPressed == true) {
				direction = "right";
			}
				
			// Check tile collision
			collisionOn = false;
			gp.cChecker.checkTile(this);
			
			//Check object collision
			int objIndex = gp.cChecker.checkObject(this, true);
			pickUpObject(objIndex);
				
			//check npc collision
			int npcIndex = gp.cChecker.checkEntity(this, gp.npc);
			interactNPC(npcIndex);
			
			//check monster collision
			int monsterIndex = gp.cChecker.checkEntity(this, gp.monster);
			contactMonster(monsterIndex);
			
			//check event
			gp.eHandler.checkEvent();
			
			// If collision is false, player can move
			if(collisionOn == false && keyH.enterPressed == false) {
								
				switch(direction) {
					case "up":
						worldY -= speed;
						break;
								
					case "down":
						worldY += speed;
						break;
									
					case "left":
						worldX -= speed;
						break;
									
					case "right":
						worldX += speed;
						break;
				}
			}
			
			if(keyH.enterPressed ==true && attackCanceled == false) {
				attacking = true;
				spriteCounter = 0;
				
			}
			
			attackCanceled = false;
			gp.keyH.enterPressed = false;
							
			spriteCounter++;
			if(spriteCounter > 12) {
				if(spriteNum == 1) {
					spriteNum = 2;
				} else if(spriteNum == 2) {
					spriteNum = 1;
				}
				spriteCounter = 0;
			}
		}
		else {
			standCounter ++;
			if(standCounter == 20) {
				spriteNum = 1;
				standCounter = 0;
			}
		}
		
		if(gp.keyH.shotKeyPressed == true && projectile.alive == false 
				&& shotAvaliableCounter == 30 && projectile.haveResource(this) == true) {
			
			projectile.set(worldX, worldY, direction, true, this);
			
			projectile.subtrackResource(this);
			
			gp.projectileList.add(projectile);
			
			shotAvaliableCounter = 0;
			
			gp.playSE(9);
		}
		
		//this needs to be outside of key if statement
		if(invincible == true) {
			invincibleCounter ++;
			if(invincibleCounter > 60) {
				invincible = false;
				invincibleCounter = 0;
			}
		}
		if(shotAvaliableCounter < 30) {
			shotAvaliableCounter++;
		}
		if(life > maxLife) {
			life = maxLife;
		}
		if(mana > maxMana) {
			mana = maxMana;
		}
		if(life <= 0 ) {
			gp.gameState = gp.gameOverState;
			gp.ui.commandNum = -1;
			gp.stopMusic();
			gp.playSE(10);
		}
	}
	
	public void attacking() {
		
		spriteCounter++;
		
		if(spriteCounter <= 5) {
			spriteNum = 1;
		}
		if(spriteCounter > 5 && spriteCounter <= 25) {
			spriteNum = 2;
			
			//Save the current worldX, worldY, solidArea
			int currentWorldX = worldX;
			int currentWorldY = worldY;
			int solidAreaWidth = solidArea.width;
			int solidAreaHeight = solidArea.height;
			
			//Adjust player's worldX/Y for the attackArea
			switch (direction) {
			case "up": worldY -= attackArea.height; break;
			case "down": worldY += attackArea.height; break;
			case "left": worldX -= attackArea.width; break;
			case "right": worldX += attackArea.width; break;
			}
			
			solidArea.width = attackArea.width;
			solidArea.height = attackArea.height;
			
			int monsterIndex = gp.cChecker.checkEntity(this, gp.monster);
			damageMonster(monsterIndex, attack);
			
			int iTileIndex = gp.cChecker.checkEntity(this, gp.iTile);
			damageInteractiveTile(iTileIndex);
			
			worldX = currentWorldX;
			worldY = currentWorldY;
			solidArea.width = solidAreaWidth;
			solidArea.height = solidAreaHeight;
		}
		if(spriteCounter > 25) {
			spriteNum = 1;
			spriteCounter = 0;
			attacking = false;
		}
	}
	
	public void pickUpObject(int i) {
		if(i !=  999) {
			if(gp.obj[gp.currentMap][i].type == type_pickupOnly) {
				
				gp.obj[gp.currentMap][i].use(this);
				gp.obj[gp.currentMap][i] = null;
			}
			else {
				if(inventory.size() != maxInventorySize) {
					inventory.add(gp.obj[gp.currentMap][i]);
					gp.playSE(1);
					gp.ui.addMessage("Got a " + gp.obj[gp.currentMap][i].name);
				}
				gp.obj[gp.currentMap][i] = null;
			}
		}
	}
	
	public void interactNPC(int i) {
		
		if(gp.keyH.enterPressed == true) {
			if(i !=  999) {
				attackCanceled = true;
//				gp.gameState = gp.dialogueState;
	
			}
		}
	}
	
	public void contactMonster(int i) {
		if(i != 999) {
			if(invincible == false && gp.monster[gp.currentMap][i].dying ==false) {
				gp.playSE(6);
				
				int damage = gp.monster[gp.currentMap][i].attack - defense;
				if(damage < 0) {
					damage = 0;
				}
				
				life -= 1;
				invincible = true; 
			}
		}
	}
	
	public void damageMonster(int i, int attack) {
		
		if(i != 999) {
			if(gp.monster[gp.currentMap][i].invincible == false) {
				gp.playSE(5);
				
				int damage = attack - gp.monster[gp.currentMap][i].defense;
				if(damage <= 0) {
					damage = 1;
				}
				
				gp.monster[gp.currentMap][i].life -= damage;
				gp.ui.addMessage(damage + " damage!");
				
				
				gp.monster[gp.currentMap][i].invincible = true;
				gp.monster[gp.currentMap][i].damageReaction();
				
				if(gp.monster[gp.currentMap][i].life <= 0) {
					gp.monster[gp.currentMap][i].dying = true;
					gp.ui.addMessage("Killed the " + gp.monster[gp.currentMap][i].name + "!");
					exp += gp.monster[gp.currentMap][i].exp;
					gp.ui.addMessage("Exp + " + gp.monster[gp.currentMap][i].exp);
					checkLevelUp();
				}
			}
		}
	}
	
	public void damageInteractiveTile(int i) {
		if(i != 999 && gp.iTile[gp.currentMap][i].destructible == true 
				&& gp.iTile[gp.currentMap][i].isCorrectItem(this) == true && gp.iTile[gp.currentMap][i].invincible == false) {
			
			gp.iTile[gp.currentMap][i].playSE();
			gp.iTile[gp.currentMap][i].life--;
			gp.iTile[gp.currentMap][i].invincible = true;
			
			if(gp.iTile[gp.currentMap][i].life <= 0) {
				gp.iTile[gp.currentMap][i] = gp.iTile[gp.currentMap][i].getDestroyedForm();
			}
			
			
		}
	}
	
	public void checkLevelUp() {
		
		if(exp >= nextLevelExp) {
			level++;
			nextLevelExp = nextLevelExp * 2;
			maxLife += 3;
			maxMana += 2;
			strength ++;
			dexterity ++;
			attack = getAttact();
			defense = getDefense();
			
			gp.playSE(7);
			gp.ui.addMessage("Level up!");
		}
	}
	
	public void selectItem() {
		
		int itemIndex = gp.ui.getItemIndexOnSlot();
		
		if(itemIndex < inventory.size()) {
			Entity selectedItem = inventory.get(itemIndex);
			
			if(selectedItem.type == type_sword || selectedItem.type == type_axe) {
				currentWeapon = selectedItem;
				attack = getAttact();
				getPlayerAttackImage();
			}
			if(selectedItem.type == type_shield) {
				currentShield = selectedItem;
				defense = getDefense();
			}
			if(selectedItem.type == type_armor) {
				currentArmor = selectedItem;
				defense = getDefense();
			}
			if(selectedItem.type == type_helmet) {
				currentHelmet = selectedItem;
				defense = getDefense();
				
			}
			if(selectedItem.type == type_consumable) {
				selectedItem.use(this);
				inventory.remove(itemIndex);
			}
		}
		
	}
	
	public void draw(Graphics2D g2) {
		
		BufferedImage image = null;
		int tempScreenX = screenX;
		int tempScreenY = screenY;
		
		switch(direction) {
		case "up":
			if(attacking == false) {
				if(spriteNum == 1) {image = up1;} 
				if(spriteNum == 2) {image = up2;}
			}
			if(attacking == true) {
				tempScreenY = screenY - gp.tileSize;
				if(spriteNum == 1) {image = attackUp1;} 
				if(spriteNum == 2) {image = attackUp2;}
			}
			break;
		case "down":
			if(attacking == false) {
				if(spriteNum == 1) {image = down1;} 
				if(spriteNum == 2) {image = down2;}
			}
			if(attacking == true) {
				if(spriteNum == 1) {image = attackDown1;} 
				if(spriteNum == 2) {image = attackDown2;}
			}
			break;
		case "left":
			if(attacking == false) {
				if(spriteNum == 1) {image = left1;} 
				if(spriteNum == 2) {image = left2;}
			}
			if(attacking == true) {
				tempScreenX = screenX - gp.tileSize;
				if(spriteNum == 1) {image = attackLeft1;} 
				if(spriteNum == 2) {image = attackLeft2;}
			}
			break;
		case "right":
			if(attacking == false) {
				if(spriteNum == 1) {image = right1;} 
				if(spriteNum == 2) {image = right2;}
			}
			if(attacking == true) {
				if(spriteNum == 1) {image = attackRight1;} 
				if(spriteNum == 2) {image = attackRight2;}
			}
			break;
		}
		
		
		if(invincible == true) {
			g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f));
		}
		
		g2.drawImage(image, tempScreenX, tempScreenY, null);
		
		//reset alpha
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
//		g2.setColor(Color.red);
//		g2.drawRect(screenX + solidArea.x, screenY + solidArea.y, solidArea.width, solidArea.height);
		
		//debug
//		g2.setFont(new Font("Arial", Font.PLAIN, 26));
//		g2.setColor(Color.white);
//		g2.drawString("Invincibler: " + invincibleCounter, 10, 400);
	}
}
