package io.github.StardewValley.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import io.github.StardewValley.Main;
import io.github.StardewValley.controllers.GameMenuController;
import io.github.StardewValley.models.App;
import io.github.StardewValley.models.enums.GameMenuCommands;
import io.github.StardewValley.models.enums.Menu;

import java.util.Scanner;
import java.util.regex.Matcher;

public class GameMenu implements AppMenu, Screen {
    private final GameMenuController controller;
    private Stage stage;
    private Skin skin;
    private final Table table;
    private final Table buttons;
    private final Table Users;
    private final Label error;
    private final TextButton MenuTitle;
    private final TextButton startGame;
    private final TextButton backButton;
    private final TextButton addUser;
    private final TextField UserName;
    private TextButton User1;
    private TextButton User2;
    private TextButton User3;
    private TextButton User4;
    private  TextButton deleteUser1;
    private TextButton deleteUser2;
    private TextButton deleteUser3;
    private TextButton deleteUser4;
    private TextButton loadGame;
    private Scanner scanner = new Scanner(System.in);




    public GameMenu(GameMenuController controller, Skin skin) {
        this.skin = skin;
        this.controller = controller;
        this.table = new Table();
        this.Users =new Table();
        this.buttons = new Table();
        this.error = new Label("", skin);
        error.setColor(1,0,0,1);
        MenuTitle = new TextButton("GameMenu", skin);
        MenuTitle.setColor(0,1,0,1);
        startGame = new TextButton("StartGame", skin);
        startGame.setColor(0,0,1,1);
        backButton = new TextButton("Back", skin);
        addUser = new TextButton("addUser", skin);
        addUser.setColor(0,0,1,1);
        UserName = new TextField("", skin);
        UserName.setMessageText("Enter Username of your friend");
        loadGame = new TextButton("Load last Game", skin);
        loadGame.setColor(0,0,1,1);
        User1 = new TextButton(App.getLoggedInUser().getUsername(), skin);
        User1.setColor(0,1,0,1);
        User2 = new TextButton("-", skin);
        User2.setColor(0,1,0,1);
        User3 = new TextButton("-", skin);
        User3.setColor(0,1,0,1);
        User4 = new TextButton("-", skin);
        User4.setColor(0,1,0,1);
        deleteUser1 = new TextButton("><", skin );
        deleteUser1.setColor(1,0,0,1);
        deleteUser2 = new TextButton("><", skin);
        deleteUser2.setColor(1,0,0,1);
        deleteUser3 = new TextButton("><", skin);
        deleteUser3.setColor(1,0,0,1);
        deleteUser4 = new TextButton("><", skin);
        deleteUser4.setColor(1,0,0,1);
        controller.setView(this);




    }
    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        table.setFillParent(true);
        buttons.setFillParent(true);
        Users.setFillParent(true);
        table.left().top();
        table.add(MenuTitle);
        Users.center().top();
        Users.row().pad(10, 0, 10, 0);
        Users.add(User1).width(200);
        Users.add(User2).width(200);
        Users.add(User3).width(200);
        Users.add(User4).width(200);
        Users.row().pad(10, 0, 10, 0);
        Users.add(deleteUser1).width(100);
        Users.add(deleteUser2).width(100);
        Users.add(deleteUser3).width(100);
        Users.add(deleteUser4).width(100);
        buttons.center();
        error.setPosition(750, 650);
        buttons.row().pad(10, 0, 10, 0);
        buttons.add(startGame).width(300);
        buttons.add(addUser).width(300);
        buttons.add(UserName).width(300);
        buttons.row().pad(10, 0, 10, 0);
        buttons.add(loadGame).width(300);
        stage.addActor(error);
        stage.addActor(backButton);
        stage.addActor(Users);
        stage.addActor(table);
        stage.addActor(buttons);
    }

    @Override
    public void render(float v) {
        ScreenUtils.clear(0, 0, 0, 1);
        Main.getBatch().begin();
        Main.getBatch().end();
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
    }


    public void run(Scanner scanner) {
        String command = scanner.nextLine();
        Matcher matcher;
//        if ((matcher = GameMenuCommands.StartNewGame.getMatcher(command)) != null) {
//            System.out.println(controller.newGame(matcher.group("username1"),
//                matcher.group("username2"),
//                matcher.group("username3"),
//                matcher.group("rest"),
//                scanner));
//        } else
        if ((matcher = GameMenuCommands.ExitGame.getMatcher(command)) != null) {
            System.out.println(controller.exitGame());
        }  else if ((matcher = GameMenuCommands.NextTurn.getMatcher(command)) != null) {
            System.out.println(controller.nextTurn());
        } else if ((matcher = GameMenuCommands.Time.getMatcher(command)) != null) {
            System.out.println(controller.getTime());
        } else if ((matcher = GameMenuCommands.Date.getMatcher(command)) != null) {
            System.out.println(controller.getDate());
        } else if ((matcher = GameMenuCommands.DateTime.getMatcher(command)) != null) {
            System.out.println(controller.getDateTime());
        } else if ((matcher = GameMenuCommands.DayOfTheWeek.getMatcher(command)) != null) {
            System.out.println(controller.getDayOfTheWeek());
        } else if ((matcher = GameMenuCommands.CheatAdvanceTime.getMatcher(command)) != null) {
            System.out.println(controller.changeTime(
                matcher.group("hour")
            ));
        } else if ((matcher = GameMenuCommands.CheatAdvanceDate.getMatcher(command)) != null) {
            System.out.println(controller.changeDate(
                matcher.group("day")
            ));
        } else if ((matcher = GameMenuCommands.Season.getMatcher(command)) != null) {
            System.out.println(controller.getSeason());
        } else if ((matcher = GameMenuCommands.CheatThor.getMatcher(command)) != null) {
            System.out.println(controller.cheatThor(
                Integer.parseInt(matcher.group("x")),
                Integer.parseInt(matcher.group("y"))
            ));
        } else if ((matcher = GameMenuCommands.Weather.getMatcher(command)) != null) {
            System.out.println(controller.getWeather());
        } else if ((matcher = GameMenuCommands.WeatherForecast.getMatcher(command)) != null) {
            System.out.println(controller.weatherForeCast());
        } else if ((matcher = GameMenuCommands.CheatWeatherSet.getMatcher(command)) != null) {
            System.out.println(controller.changeWeather(
                matcher.group("type")
            ));
        }

        //For Energy
        else if ((matcher = GameMenuCommands.EnergyShow.getMatcher(command)) != null) {
            System.out.println(controller.energyShow());
        } else if ((matcher = GameMenuCommands.EnergySet.getMatcher(command)) != null) {
            System.out.println(controller.energySet(matcher.group("value")));
        } else if ((matcher = GameMenuCommands.EnergyUnlimited.getMatcher(command)) != null) {
            System.out.println(controller.energyUnlimited());
        }


        //Tool
        else if ((matcher = GameMenuCommands.ToolsEquip.getMatcher(command))!= null) {
            System.out.println(controller.toolEquip(
                matcher.group("toolName")
            ));
        } else if ((matcher = GameMenuCommands.ToolsShowCurrent.getMatcher(command))!= null) {
            System.out.println(controller.currentToolShow());
        } else if ((matcher = GameMenuCommands.ToolsShowAvailable.getMatcher(command))!= null) {
            System.out.println(controller.toolsShowAvailable());
        } else if ((matcher = GameMenuCommands.ToolsUpgrade.getMatcher(command))!= null) {
            System.out.println(controller.toolUpgrade(
                matcher.group("toolName")
            ));
        }
//         else if ((matcher = GameMenuCommands.ToolsUse.getMatcher(command))!= null) {
//            System.out.println(controller.toolUse(
//                matcher.group("direction")
//            ));
//        }

        //For Inventory
        else if ((matcher = GameMenuCommands.InventoryShow.getMatcher(command)) != null) {
            System.out.println(controller.inventoryShow());
        } else if ((matcher = GameMenuCommands.InventoryTrash.getMatcher(command)) != null) {
            System.out.println(controller.inventoryTrash(
                matcher.group("itemName"),
                matcher.group("number")
            ));
        }

        //For Plants
        else if ((matcher = GameMenuCommands.CraftInfo.getMatcher(command)) != null) {
            System.out.println(controller.craftInfo(
                matcher.group("craftName")
            ));
        } else if ((matcher = GameMenuCommands.Plant.getMatcher(command)) != null) {
            System.out.println(controller.plantSeed(
                matcher.group("seed"),
                matcher.group("direction")
            ));
        } else if ((matcher = GameMenuCommands.ShowPlant.getMatcher(command)) != null) {
            System.out.println(controller.showPlant(
                matcher.group("x"),
                matcher.group("y")
            ));
        } else if ((matcher = GameMenuCommands.Fertilize.getMatcher(command)) != null) {
            System.out.println(controller.fertilize(
                matcher.group("fertilizer"),
                matcher.group("direction")
            ));
        } else if ((matcher = GameMenuCommands.HowMuchWater.getMatcher(command)) != null) {
            System.out.println(controller.howMuchWater());
        }


        //For crafting
        else if ((matcher = GameMenuCommands.CraftingShowRecipes.getMatcher(command)) != null) {
            System.out.println(controller.craftingShowRecipes());
        } else if ((matcher = GameMenuCommands.CraftingCraft.getMatcher(command)) != null) {
            System.out.println(controller.craftingCraft(matcher.group("itemName")));
        }
//        else if((matcher = GameMenuCommands.PlaceItem.getMatcher(command)) != null) {
//            System.out.println(controller.placeItem(matcher.group("itemName"),matcher.group("direction")));
//        }
        else if((matcher = GameMenuCommands.CheatAddItem.getMatcher(command)) != null) {
            System.out.println(controller.addItem(matcher.group("itemName"),matcher.group("count")));
        }
        //For Animal
        else if((matcher = GameMenuCommands.Build.getMatcher(command)) != null) {
            System.out.println(controller.build(matcher.group("buildName"),
                matcher.group("x"), matcher.group("y")));
        }
        else if((matcher = GameMenuCommands.BuyAnimal.getMatcher(command)) != null) {
            System.out.println(controller.buyAnimal(matcher.group("animal") , matcher.group("name")));
        }
        else if((matcher = GameMenuCommands.Pet.getMatcher(command)) != null) {
            System.out.println(controller.pet(matcher.group("name")));
        }
        else if((matcher = GameMenuCommands.CheatSetFriendshipWithAnimal.getMatcher(command)) != null){
            System.out.println(controller.setFriendship(matcher.group("animalName"),
                matcher.group("amount")));
        }
        else if((matcher = GameMenuCommands.ShowAnimals.getMatcher(command)) != null) {
            System.out.println(controller.animals());
        }
        else if((matcher = GameMenuCommands.ShepherdAnimal.getMatcher(command)) != null) {
            System.out.println(controller.shepherdAnimal(matcher.group("animalName"),
                matcher.group("x"), matcher.group("y")));
        }
        else if((matcher = GameMenuCommands.FeedHay.getMatcher(command)) != null) {
            System.out.println(controller.feedHay(matcher.group("animalName")));
        }
        else if((matcher = GameMenuCommands.Produces.getMatcher(command)) != null) {
            System.out.println(controller.produces());
        }
        else if((matcher = GameMenuCommands.CollectProduce.getMatcher(command)) != null) {
            System.out.println(controller.collectProduct(matcher.group("animalName")));
        }
        else if((matcher = GameMenuCommands.SellAnimal.getMatcher(command)) != null) {
            System.out.println(controller.sellAnimal(matcher.group("animalName")));
        }

        //For fishing
//        else if((matcher = GameMenuCommands.Fishing.getMatcher(command)) != null) {
//            System.out.println(controller.fishing(matcher.group("fishingPole")));
//        }



        // build greenhouse
        else if ((matcher = GameMenuCommands.GreenhouseBuild.getMatcher(command)) != null) {
            System.out.println(controller.buildGreenHouse());
        }
        // move player
        else if ((matcher = GameMenuCommands.Walk.getMatcher(command)) != null) {
            System.out.println(controller.walk(Integer.parseInt(matcher.group("x"))
                , Integer.parseInt(matcher.group("y")), scanner));
        }
        // print map
        else if ((matcher = GameMenuCommands.PrintMap.getMatcher(command)) != null) {
            controller.printMap(Integer.parseInt(matcher.group("x"))
                , Integer.parseInt(matcher.group("y"))
                , Integer.parseInt(matcher.group("size")));
        } else if (command.trim().equals("help reading map")) {
            controller.helpReadingMap();
        }
        //cooking
        else if ((matcher = GameMenuCommands.CookingRefrigerator.getMatcher(command)) != null) {
            System.out.println(controller.cookingRefrigerator(matcher.group("mod"), matcher.group("item")));
        } else if ((matcher = GameMenuCommands.CookingShowRecipes.getMatcher(command)) != null) {
            System.out.println(controller.cookingShowRecipes());
        } else if ((matcher = GameMenuCommands.CookingPrepare.getMatcher(command)) != null) {
            System.out.println(controller.cookingPrepare(
                matcher.group("recipeName")
            ));
        } else if ((matcher = GameMenuCommands.Eat.getMatcher(command)) != null) {
            System.out.println(controller.eat(
                matcher.group("foodName")
            ));
        }

        else if ((matcher = GameMenuCommands.CheatAddDollars.getMatcher(command)) != null) {
            System.out.println(controller.cheatAddDollars(
                matcher.group("count")
            ));
        }

        // friendship
//        else if (command.trim().equals("friendships")) {
//            System.out.println(controller.friendship());
//        }
        else if ((matcher = GameMenuCommands.talk.getMatcher(command)) != null) {
            System.out.println(controller.talk(matcher.group("username"), matcher.group("message").trim()));
        } else if ((matcher = GameMenuCommands.talkHistory.getMatcher(command)) != null) {
            System.out.println(controller.talkHistory(matcher.group("username").trim()));
        } else if ((matcher = GameMenuCommands.gift.getMatcher(command)) != null) {
            System.out.println(controller.gift(matcher.group("username").trim()
                , matcher.group("item").trim()
                , matcher.group("amount").trim()));
        } else if ((matcher = GameMenuCommands.hug.getMatcher(command)) != null) {
            System.out.println(controller.hug(matcher.group("username").trim()));
        } else if (command.trim().equals("gift list")) {
            System.out.println(controller.giftList());
        } else if ((matcher = GameMenuCommands.giftRate.getMatcher(command)) != null) {
            System.out.println(controller.giftRate(matcher.group("giftNumber"), matcher.group("rate")));
        } else if ((matcher = GameMenuCommands.giftHistory.getMatcher(command)) != null) {
            System.out.println(controller.giftHistory(matcher.group("username")));
        }else if ((matcher = GameMenuCommands.flower.getMatcher(command)) != null) {
            System.out.println(controller.flower(matcher.group("username")));
        }else if ((matcher = GameMenuCommands.askMarriage.getMatcher(command)) != null) {
            System.out.println(controller.askMarriage(matcher.group("username"),matcher.group("ring")));
        } else if((matcher = GameMenuCommands.respond.getMatcher(command)) != null) {
            System.out.println(controller.respond(matcher.group("accept"),matcher.group("username")));
        }else if (command.trim().equals("my messages")) {
            System.out.println(controller.showMessage());
        } else if ((matcher = GameMenuCommands.deleteMessage.getMatcher(command)) != null) {
            System.out.println(controller.deleteMessage(Integer.parseInt(matcher.group("index"))));
        } else if ((matcher = GameMenuCommands.startTrade.getMatcher(command))!=null) {
            System.out.println(controller.startTrade());
            //App.setCurrentMenu(Menu.TradeMenu);
        }
        // for NPC
        else if ((matcher = GameMenuCommands.meetNPC.getMatcher(command)) != null) {
            System.out.println(controller.meetNPC(matcher.group("npcName"),scanner));
        }
//        else if ((matcher = GameMenuCommands.giftNPC.getMatcher(command)) != null) {
//            System.out.println(controller.giftNPC(matcher));
//        }
//        else if (command.trim().equals("friendship NPC list")) {
//            System.out.println(controller.friendshipNPCList());
//        }
        else if (command.trim().equals("quests list")) {
            System.out.println(controller.questsList());
        }else if ((matcher = GameMenuCommands.questFinish.getMatcher(command)) != null) {
            System.out.println(controller.questFinish(matcher.group("index")));
        }
        else if (command.trim().equals("show current menu")) {
            //System.out.println(App.getCurrentMenu().name());
        }else if  (command.trim().equals("exit game")) {
            System.out.println(controller.exitGame());
        }
        else if (command.trim().equals("delete and exit game")) {
            System.out.println(controller.deleteAndExitThisGame(scanner));
        }
        else {
            System.out.println("invalid command");
        }

    }



    @Override
    public void resize(int i, int i1) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }

    public GameMenuController getController() {
        return controller;
    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public Skin getSkin() {
        return skin;
    }

    public void setSkin(Skin skin) {
        this.skin = skin;
    }

    public Table getTable() {
        return table;
    }

    public Table getButtons() {
        return buttons;
    }

    public Label getError() {
        return error;
    }

    public TextButton getMenuTitle() {
        return MenuTitle;
    }

    public TextButton getStartGame() {
        return startGame;
    }

    public TextButton getBackButton() {
        return backButton;
    }

    public TextButton getAddUser() {
        return addUser;
    }

    public TextField getUserName() {
        return UserName;
    }

    public TextButton getUser1() {
        return User1;
    }

    public void setUser1(TextButton user1) {
        User1 = user1;
    }

    public TextButton getUser2() {
        return User2;
    }

    public void setUser2(TextButton user2) {
        User2 = user2;
    }

    public TextButton getUser3() {
        return User3;
    }

    public void setUser3(TextButton user3) {
        User3 = user3;
    }

    public TextButton getUser4() {
        return User4;
    }

    public void setUser4(TextButton user4) {
        User4 = user4;
    }

    public TextButton getDeleteUser1() {
        return deleteUser1;
    }

    public void setDeleteUser1(TextButton deleteUser1) {
        this.deleteUser1 = deleteUser1;
    }

    public TextButton getDeleteUser2() {
        return deleteUser2;
    }

    public void setDeleteUser2(TextButton deleteUser2) {
        this.deleteUser2 = deleteUser2;
    }

    public TextButton getDeleteUser3() {
        return deleteUser3;
    }

    public void setDeleteUser3(TextButton deleteUser3) {
        this.deleteUser3 = deleteUser3;
    }

    public TextButton getDeleteUser4() {
        return deleteUser4;
    }

    public void setDeleteUser4(TextButton deleteUser4) {
        this.deleteUser4 = deleteUser4;
    }

    public Table getUsers() {
        return Users;
    }
    public void setError(String error) {
        this.error.setText(error);
    }

    public Scanner getScanner() {
        return scanner;
    }

    public void setScanner(Scanner scanner) {
        this.scanner = scanner;
    }

    public TextButton getLoadGame() {
        return loadGame;
    }
}
