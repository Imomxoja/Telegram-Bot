package org.example.bot;

import org.example.domain.DTO.BaseResponse;
import org.example.domain.DTO.UserCreateDto;
import org.example.domain.DTO.UserStateDto;
import org.example.domain.model.*;
import org.example.service.basket.BasketService;
import org.example.service.basket.BasketServiceImpl;
import org.example.service.order.OrderService;
import org.example.service.order.OrderServiceImpl;
import org.example.service.product.ProductService;
import org.example.service.product.ProductServiceImpl;
import org.example.service.user.UserService;
import org.example.service.user.UserServiceImpl;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Contact;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;



public class ShopBotService {
    private final UserService userService = new UserServiceImpl();
    private final ProductService productService = new ProductServiceImpl();
    private final BasketService basketService = new BasketServiceImpl();
    private final OrderService orderService = new OrderServiceImpl();
    SendMessage message = new SendMessage();

    public SendMessage main(String chatId) {
        SendMessage message = new SendMessage();
        message.setText("<b><i>Choose one</i></b>");
        message.setParseMode("HTML");
        message.setChatId(chatId);
        message.setReplyMarkup(mainMenu());
        return message;
    }

    public SendMessage registerUser(String chatId, Contact contact) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);

        UserCreateDto userCreateDTO = new UserCreateDto(
                contact.getFirstName() + " " + contact.getLastName(),
                contact.getPhoneNumber(), chatId);
        BaseResponse response = userService.save(userCreateDTO);

        if (response.getStatus() == 200) {
            userService.updateState(new UserStateDto(chatId, UserState.REGISTERED));
            sendMessage.setReplyMarkup(mainMenu());
        }

        sendMessage.setText(response.getMessage());
        return sendMessage;
    }

    public UserState checkState(String chatId) {
        BaseResponse<UserStateDto> response = userService.getUserState(chatId);
        if (response.getStatus() == 200) {
            return response.getData().state();
        }
        return UserState.START;
    }

    public SendMessage shareContact(String chatId) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText("<b><i>Please register</i></b>");
        sendMessage.setParseMode("HTML");
        sendMessage.setReplyMarkup(requestContact());
        return sendMessage;
    }


    public ReplyKeyboardMarkup requestContact() {
        ReplyKeyboardMarkup markup = new ReplyKeyboardMarkup();
        KeyboardRow keyboardRow = new KeyboardRow();
        KeyboardButton button = new KeyboardButton("Please share phone number 📞");
        button.setRequestContact(true);
        keyboardRow.add(button);

        markup.setResizeKeyboard(true);
        markup.setKeyboard(List.of(keyboardRow));
        return markup;
    }

    public ReplyKeyboardMarkup mainMenu() {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();

        List<KeyboardRow> keyboardRows = new ArrayList<>();

        KeyboardRow row = new KeyboardRow();
        row.add("\uD83D\uDCCB Categories");
        keyboardRows.add(row);

        row = new KeyboardRow();
        row.add("\uD83D\uDCD1 Orders");
        keyboardRows.add(row);

        row = new KeyboardRow();
        row.add("\uD83D\uDED2 Basket");
        keyboardRows.add(row);

        replyKeyboardMarkup.setKeyboard(keyboardRows);
        return replyKeyboardMarkup;
    }

    public UserState updateState(String chatId, String text) {
        UserState state = null;
        switch (text) {
            case "\uD83D\uDCCB Categories" -> state = UserState.CATEGORIES;
            case "\uD83D\uDCD1 Orders" -> state = UserState.ORDERS;
            case "\uD83D\uDED2 Basket" -> state = UserState.BASKET;
        }

        userService.updateUserState(chatId, state);
        return state;
    }

    public SendMessage categories(String chatId) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setText("<b><i>Categories</i></b>");
        sendMessage.setParseMode("HTML");
        sendMessage.setChatId(chatId);
        sendMessage.setReplyMarkup(categoriesMarkUp());

        return sendMessage;
    }

    public InlineKeyboardMarkup categoriesMarkUp() {
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();

        List<List<InlineKeyboardButton>> rows = new ArrayList<>();
        List<InlineKeyboardButton> row = new ArrayList<>();

        InlineKeyboardButton button = new InlineKeyboardButton();

        button.setText("\uD83D\uDDA5 PC");
        button.setCallbackData("PC");
        row.add(button);

        button = new InlineKeyboardButton();
        button.setText("\uD83D\uDCBB Laptop");
        button.setCallbackData("laptop");
        row.add(button);

        rows.add(row);
        row = new ArrayList<>();

        button = new InlineKeyboardButton();
        button.setText("\uD83D\uDCF1 Phone");
        button.setCallbackData("phone");
        row.add(button);


        button = new InlineKeyboardButton();
        button.setText("\uD83D\uDCFA TV");
        button.setCallbackData("tv");
        row.add(button);

        rows.add(row);
        row = new ArrayList<>();
        button = new InlineKeyboardButton();
        button.setText("\uD83D\uDD19 Back to menu");
        button.setCallbackData("back to menu");
        row.add(button);

        rows.add(row);

        markup.setKeyboard(rows);
        return markup;
    }

    String type1;

    public SendMessage Products(Long chatId, String type) {
        type1 = type;
        message.setText("<i><b>\uD83D\uDD3D Which one you want to add to cart \uD83D\uDD3D</b></i>");
        message.setParseMode("HTML");
        message.setChatId(chatId.toString());
        message.setReplyMarkup(markup(type));

        return message;
    }


    public InlineKeyboardMarkup markup(String type) {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rows = new ArrayList<>();
        List<InlineKeyboardButton> row = new ArrayList<>();

        InlineKeyboardButton button = new InlineKeyboardButton();

        ArrayList<Product> products;
        try {
            products = productService.getProducts();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        for (Product product : products) {
            if (product.getType().name().equals(type)) {
                button.setText(product.getName());
                button.setCallbackData(product.getName());
                row.add(button);
            }
            button = new InlineKeyboardButton();
            row = new ArrayList<>();
            rows.add(row);
        }
        rows.add(row);
        row = new ArrayList<>();
        button = new InlineKeyboardButton();

        button.setText("\uD83D\uDD19 Back to categories");
        button.setCallbackData("categories");
        row.add(button);
        rows.add(row);

        inlineKeyboardMarkup.setKeyboard(rows);
        return inlineKeyboardMarkup;
    }

    String prodName1;

    public SendMessage AddBasket(Long chatId, String productName) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setText(productName);
        sendMessage.setChatId(chatId.toString());
        sendMessage.setReplyMarkup(AddBasketMarkUp());
        prodName1 = productName;
        return sendMessage;
    }

    public InlineKeyboardMarkup AddBasketMarkUp() {
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rows = new ArrayList<>();
        List<InlineKeyboardButton> row = new ArrayList<>();


        InlineKeyboardButton button = new InlineKeyboardButton();
        button.setText("1");
        button.setCallbackData("1");
        row.add(button);

        button = new InlineKeyboardButton();
        button.setText("2");
        button.setCallbackData("2");
        row.add(button);

        button = new InlineKeyboardButton();
        button.setText("3");
        button.setCallbackData("3");
        row.add(button);
        rows.add(row);

        row = new ArrayList<>();
        button = new InlineKeyboardButton();
        button.setText("\uD83D\uDD19 Back to products");
        button.setCallbackData("products");
        row.add(button);

        rows.add(row);
        markup.setKeyboard(rows);
        return markup;
    }


    public SendMessage Basket(Long chatId, int amount) {
        SendMessage sendMessage = new SendMessage();

        String name = prodName1;

        User user = userService.getByChatId(chatId.toString());
        Product product = productService.getByName(name);

        Basket basket = new Basket()
                .setUserId(user.getId())
                .setProductId(product.getId())
                .setAmount(amount)
                .setProductName(product.getName())
                .setTotalPrice(amount * product.getPrice());

        int response = basketService.saveBasket(basket);

        if (response == 1) {
            sendMessage.setText("Successfully added to basket");
            sendMessage.setChatId(chatId.toString());
            sendMessage.setReplyMarkup(backAddBasket());
            updateState(chatId.toString(), "\uD83D\uDED2 Basket");
            return sendMessage;
        }

        sendMessage.setChatId(chatId.toString());
        sendMessage.setText("Fail");
        sendMessage.setReplyMarkup(backAddBasket());

        return sendMessage;
    }

    public InlineKeyboardMarkup backAddBasket() {
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rows = new ArrayList<>();
        List<InlineKeyboardButton> row = new ArrayList<>();
        InlineKeyboardButton button = new InlineKeyboardButton();

        button.setText("\uD83D\uDD19 Back");
        button.setCallbackData("back");
        row.add(button);
        rows.add(row);

        markup.setKeyboard(rows);
        return markup;
    }


    public SendMessage userBasket(String chatId) {
        User user = userService.getByChatId(message.getChatId());
        ArrayList<Basket> userBaskets = basketService.getUserBaskets(user.getId());

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);

        if (!userBaskets.isEmpty()) {
            sendMessage.setText("<b><i>Your basket \uD83D\uDED2</i></b>");
            sendMessage.setParseMode("HTML");
            sendMessage.setReplyMarkup(basketMarkUp(userBaskets));
        } else {
            sendMessage.setText("<b><i>No Products \uD83D\uDE41</i></b>");
            sendMessage.setParseMode("HTML");
        }
        return sendMessage;
    }

    public InlineKeyboardMarkup basketMarkUp(ArrayList<Basket> userBaskets) {
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rows = new ArrayList<>();
        List<InlineKeyboardButton> row = new ArrayList<>();

        for (Basket basket : userBaskets) {
            InlineKeyboardButton button = new InlineKeyboardButton();
            button.setText(basket.getProductName());
            button.setCallbackData(basket.getProductId().toString());
            row.add(button);
            rows.add(row);
            row = new ArrayList<>();
        }
        rows.add(row);

        row = new ArrayList<>();
        InlineKeyboardButton button = new InlineKeyboardButton();
        button.setText("Back to menu");
        button.setCallbackData("menu");

        row.add(button);
        rows.add(row);

        markup.setKeyboard(rows);
        return markup;
    }

    public SendMessage orderProduct(String chatId, String productId) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        User user = userService.getByChatId(chatId);
        ArrayList<Basket> userBaskets = basketService.getUserBaskets(user.getId());

        for (Basket userBasket : userBaskets) {
            if (userBasket.getProductId().toString().equals(productId)) {
                BaseResponse response = orderService.orderProduct(user, userBasket);
                if (response.getStatus() == 777) {
                    sendMessage.setText("<b><i>Successfully was purchased \uD83E\uDD11</i></b>");
                    sendMessage.setParseMode("HTML");
                }
            }
        }
        return sendMessage;
    }


    public SendMessage orders(String chatId) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);

        User user = userService.getByChatId(chatId);

        ArrayList<Order> userOrders = orderService.getUserOrders(user.getId());

        if (userOrders.isEmpty()){
            sendMessage.setText("<b><i>No orders</i></b>");
            sendMessage.setParseMode("HTML");
        }

        StringBuilder orderText = new StringBuilder();
        for (Order order : userOrders) {
            orderText.append("1)\uD83D\uDCDD Product name : ").
                    append(order.getProductName()).
                    append("\n").append("2)\uD83D\uDD22 Amount : ").
                    append(order.getAmount()).append("\n").
                    append("3)\uD83D\uDCB8 Price : ").
                    append(order.getTotalPrice() / order.getAmount()).append("\n").
                    append("4)\uD83D\uDCB3 Total price : ").
                    append(order.getTotalPrice()).append("\n").
                    append("5)\uD83D\uDCC5 Date : ").
                    append(order.getCreatedDate()).
                    append("\n\n");
        }
        sendMessage.setText(orderText.toString());


        return sendMessage;
    }
}
