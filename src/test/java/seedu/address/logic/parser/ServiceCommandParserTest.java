package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.COST_DESC_1;
import static seedu.address.logic.commands.CommandTestUtil.COST_DESC_2;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_COST_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_ROOM_DESC;
import static seedu.address.logic.commands.CommandTestUtil.ITEM_NUMBER_DESC_RS01;
import static seedu.address.logic.commands.CommandTestUtil.ITEM_NUMBER_DESC_RS02;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.ROOM_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.ROOM_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_COST_1;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ITEM_NUMBER_RS01;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ROOM_NUMBER_AMY;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Optional;

import org.junit.Test;

import seedu.address.logic.commands.ServiceCommand;
import seedu.address.model.expenses.Money;
import seedu.address.model.room.RoomNumber;

public class ServiceCommandParserTest {

    private ServiceCommandParser parser = new ServiceCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        RoomNumber expectedRoomNumber = new RoomNumber(VALID_ROOM_NUMBER_AMY);
        String expectedItemNumber = VALID_ITEM_NUMBER_RS01;
        Optional<Money> expectedCost = Optional.of(new Money(VALID_COST_1));
        ServiceCommand expectedServiceCommand = new ServiceCommand(
                expectedRoomNumber, expectedItemNumber, expectedCost);

        // whitespace only preamble
        assertParseSuccess(parser,
                PREAMBLE_WHITESPACE + ROOM_DESC_AMY + ITEM_NUMBER_DESC_RS01 + COST_DESC_1,
                expectedServiceCommand);

        // multiple rooms - last room accepted
        assertParseSuccess(parser,
                 ROOM_DESC_BOB + ROOM_DESC_AMY + ITEM_NUMBER_DESC_RS01 + COST_DESC_1,
                expectedServiceCommand);

        // multiple item numbers - last item accepted
        assertParseSuccess(parser,
                ROOM_DESC_AMY + ITEM_NUMBER_DESC_RS02 + ITEM_NUMBER_DESC_RS01 + COST_DESC_1,
                expectedServiceCommand);

        // multiple costs - last cost accepted
        assertParseSuccess(parser,
                ROOM_DESC_AMY + ITEM_NUMBER_DESC_RS01 + COST_DESC_2 + COST_DESC_1,
                expectedServiceCommand);
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // no cost
        RoomNumber expectedRoomNumber = new RoomNumber(VALID_ROOM_NUMBER_AMY);
        String expectedItemNumber = VALID_ITEM_NUMBER_RS01;
        Optional<Money> expectedCost = Optional.empty();
        ServiceCommand expectedServiceCommand = new ServiceCommand(
                expectedRoomNumber, expectedItemNumber, expectedCost);
        assertParseSuccess(parser, ROOM_DESC_AMY + ITEM_NUMBER_DESC_RS01, expectedServiceCommand);
    }

    @Test
    public void parse_compulsoryFieldsMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                ServiceCommand.MESSAGE_USAGE);

        // no room number
        assertParseFailure(parser, ITEM_NUMBER_DESC_RS01, expectedMessage);
        assertParseFailure(parser, ITEM_NUMBER_DESC_RS01 + COST_DESC_1, expectedMessage);

        // no item number
        assertParseFailure(parser, ROOM_DESC_AMY, expectedMessage);
        assertParseFailure(parser, ROOM_DESC_AMY + COST_DESC_1, expectedMessage);

        // missing room prefix
        assertParseFailure(parser, VALID_ROOM_NUMBER_AMY + ITEM_NUMBER_DESC_RS01, expectedMessage);

        // missing item number prefix
        assertParseFailure(parser, ROOM_DESC_AMY + " " + VALID_ITEM_NUMBER_RS01, expectedMessage);
    }

    @Test
    public void parse_invalidValues_failure() {
        // invalid room number
        assertParseFailure(parser, INVALID_ROOM_DESC + ITEM_NUMBER_DESC_RS01,
                RoomNumber.MESSAGE_ROOM_NUMBER_CONSTRAINTS);

        // invalid cost
        assertParseFailure(parser, ROOM_DESC_AMY + ITEM_NUMBER_DESC_RS01 + INVALID_COST_DESC,
                Money.MESSAGE_MONEY_CONSTRAINTS);

        // no invalid item number since parser cannot check menu

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + ROOM_DESC_AMY + ITEM_NUMBER_DESC_RS01,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ServiceCommand.MESSAGE_USAGE));
    }
}
