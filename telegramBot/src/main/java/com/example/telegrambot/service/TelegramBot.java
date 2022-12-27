package com.example.telegrambot.service;

import com.example.telegrambot.config.BaseConfig;
import com.vdurmont.emoji.EmojiParser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class TelegramBot extends TelegramLongPollingBot {
    final BaseConfig baseConfig;
    public static final String HELP_TEXT = EmojiParser.parseToUnicode(":eye: Цей бот створив один зі студентів Харківського машинобудівного коледжу - Богдан Зарубін, використовуючи мову програмування Java, фреймворк Spring Boot і Telegram API. " +
            "Посібник з бота:");

    public String collegeListNumberOne = "Харківський машинобудівний коледж (ХМК) створено наказом Міністерства освіти України від 20.06.1997 р. №218. Коледж є правонаступником Харківського машинобудівного коледжу, Харківського машинобудівного технікуму, Харківського заочного машинобудівного технікуму, Харківського вечірнього моторобудівного технікуму, які ліквідовані при реформуванні мережі вищих навчальних закладів України.\n" +
            "\n" +
            "Коледж є власністю держави, підпорядкований Міністерству освіти і науки України.\n" +
            "\n" +
            "Право на здійснення освітньої діяльності коледжу надає Ліцензія МОН України на право надання освітніх послуг АЕ № 458775";

    public String collegeListNumberTwo = "Для публічного ознайомлення документи та інша інформація знаходяться:\n" +
            "\n" +
            "з питань організації освітнього процесу (освітньо-професійні програми, стандарти вищої освіти, навчальні плани, навчальні робочі плани, навчальні програми, положення про модульно-рейтингову систему, положення про стипендіальне забезпечення студентів, правила прийому, ліцензії та акредитаційні сертифікати) у заступника директора з навчальної роботи Супрун Тетяни Володимирівни;\n" +
            "з питань нормативного та статутного регулювання діяльності коледжу (Статут, Правила внутрішнього розпорядку, колективний договір, зразки контрактів з педагогічними працівниками) у юрисконсульта Костенко Галини Олександрівни.\n" +
            "Ознайомитись з цими документами можна в робочі дні з 8.00 до 14.00 за попереднім замовленням без будь-яких обмежень у терміни, передбачені чинним законодавством.";

    public String collegeListNumberThree = "Головний корпус\n" +
            "Адреса: вул. Плеханівська, 79" +
            " Телефони:\n" +
            "\n" +
            "Т.в.о. директора (приймальна)\t737-23-30\tМіщенко С. Г.\n" +
            "Заступник директора з навчальної роботи\t737-23-87\t \n" +
            "Приймальна комісія\t737-24-36,\n" +
            "067-387-54-24\tСелегень М.В.\n" +
            "Методичний кабінет\t737-25-24\tГолобородько С.О.\n" +
            "Відділення машинобудування, метрології, енергетики та транспорту\t737-25-24\tБирька Н.В.\n" +
            "Відділ кадрів\t737-23-10\t Ракитянська Т.В., Кузнецова Н.В.\n" +
            "Бухгалтерія\t737-83-76\t Овчаренко Н.О.\n" +
            "Вахта\t737-13-43\t" +

            "\n" +
            "\n Інженерний корпус\n" +
            "" +
            "Адреса: вул. Генерала Момота (3-го Інтернаціоналу), 5" +
            "Телефони:\n" +
            "\n" +
            "Т.в.о. директора (приймальна)\t94-09-09\tМіщенко С.Г.\n" +
            "Заступник директора з виховної роботи та практичного навчання\t94-09-09\t \n" +
            "Завідувач навчально-виробничої практики\t94-48-25\tДон Є.Ю.\n" +
            "Відділення профільної освіти\t93-41-85\tБондаренко С.В.\n" +
            "Відділення машинобудування, метрології, енергетики та транспорту\t93-41-85\tАсланян О.М.\n" +
            "Вахта\t93-51-52\t \n" +
            "Гуртожиток (комендант)\n" +
            "93-30-58\t Волькіна С.М. ";

    public TelegramBot(BaseConfig baseConfig) {
        this.baseConfig = baseConfig;
        List<BotCommand> botCommandList = new ArrayList<>();
        botCommandList.add(new BotCommand("/start", "запустити бота"));
        botCommandList.add(new BotCommand("/mydata", "ваші дані"));
        botCommandList.add(new BotCommand("/deletemydata", "видалити дані"));
        botCommandList.add(new BotCommand("/help", "команди бота"));

        try {
            this.execute(new SetMyCommands(botCommandList, new BotCommandScopeDefault(), null));
        } catch (TelegramApiException e) {
            log.error("Error setting bot`s command list: " + e.getMessage());
        }
    }

    @Override
    public String getBotUsername() {
        return baseConfig.getBotName();
    }

    @Override
    public String getBotToken() {
        return baseConfig.getBotToken();
    }

    @Override
    public void onUpdateReceived(Update update) {
        if(update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText();
            long chatId = update.getMessage().getChatId();

                switch (messageText) {
                    case "/start":

                        startCommandReceived(chatId, update.getMessage().getChat().getFirstName());
                        break;

                    case "/help":


                        sendMessage(chatId, HELP_TEXT);
                        break;

                    case "Коледж":
                        college(chatId);
                        break;

                    default:
                        String notFoundCommand = EmojiParser.parseToUnicode("Вибачте, цієї команди ще немає в боті. Ми додамо її найближчим часом! :rocket:");
                        sendMessage(chatId, notFoundCommand);
                }
        }  else if (update.hasCallbackQuery()) {
            String callbackData = update.getCallbackQuery().getData();
            long messageId = update.getCallbackQuery().getMessage().getMessageId();
            long chatId = update.getCallbackQuery().getMessage().getChatId();

            if(callbackData.equals("LIST_NUMBER_ONE")) {
                EditMessageText editMessageText = new EditMessageText();
                editMessageText.setChatId(String.valueOf(chatId));
                editMessageText.setText(collegeListNumberOne);
                editMessageText.setMessageId((int) messageId);

                try {
                    execute(editMessageText);
                } catch (TelegramApiException e) {
                    log.error("Error occurred: " + e.getMessage());
                }
            }
            else if(callbackData.equals("LIST_NUMBER_TWO")) {
                EditMessageText editMessageText = new EditMessageText();
                editMessageText.setChatId(String.valueOf(chatId));
                editMessageText.setText(collegeListNumberTwo);
                editMessageText.setMessageId((int) messageId);

                try {
                    execute(editMessageText);
                } catch (TelegramApiException e) {
                    log.error("Error occurred: " + e.getMessage());
                }
            }
            else if(callbackData.equals("LIST_NUMBER_THREE")) {
                EditMessageText editMessageText = new EditMessageText();
                editMessageText.setChatId(String.valueOf(chatId));
                editMessageText.setText(collegeListNumberThree);
                editMessageText.setMessageId((int) messageId);

                try {
                    execute(editMessageText);
                } catch (TelegramApiException e) {
                    log.error("Error occurred: " + e.getMessage());
                }
            }
        }
    }

    private void college(long chatId) {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText("Що саме вас цікавить?");

        InlineKeyboardMarkup markupInLine = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInLine = new ArrayList<>();
        List<InlineKeyboardButton> rowInLine = new ArrayList<>();
        var yesButton = new InlineKeyboardButton();

        yesButton.setText("Правова основа");
        yesButton.setCallbackData("LIST_NUMBER_ONE");

        var noButton = new InlineKeyboardButton();

        noButton.setText("Публічна інформація");
        noButton.setCallbackData("LIST_NUMBER_TWO");

        var threeButton = new InlineKeyboardButton();

        threeButton.setText("Контакти");
        threeButton.setCallbackData("LIST_NUMBER_THREE");

        rowInLine.add(yesButton);
        rowInLine.add(noButton);
        rowInLine.add(threeButton);

        rowsInLine.add(rowInLine);

        markupInLine.setKeyboard(rowsInLine);
        message.setReplyMarkup(markupInLine);

        try {
            execute(message);
        } catch (TelegramApiException e) {
            log.error("Error occurred: " + e.getMessage());
        }
    }

    private void startCommandReceived(long chatId, String name) {
        String answer = EmojiParser.parseToUnicode("Привіт, " + name + " . :relaxed: Я бот Харківського машинобудівного фахового коледжу. Ви можете дізнатися тут багато інформації про наш коледж. (P.S Якщо ви помітили баг в роботі бота, не хвилюйтеся, оновлення і поліпшення бота виходять регулярно, ми його вдосконалюємо і підтримуємо).");
        log.info("Replied to user " + name);
        sendMessage(chatId, answer);
    }

    private void sendMessage(long chatId, String textToSend) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(chatId));
        sendMessage.setText(textToSend);

        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        List<KeyboardRow> keyboardRowList = new ArrayList<>();
        KeyboardRow keyboardRow = new KeyboardRow();
        keyboardRow.add("Коледж");
        keyboardRow.add("Студентам");
        keyboardRow.add("Абітурієнтам");
        keyboardRowList.add(keyboardRow);
        replyKeyboardMarkup.setKeyboard(keyboardRowList);
        sendMessage.setReplyMarkup(replyKeyboardMarkup);

        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
             log.error("Error occurred: " + e.getMessage());
        }
    }
}
