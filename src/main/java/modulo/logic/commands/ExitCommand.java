package modulo.logic.commands;

import static modulo.commons.core.Messages.MESSAGE_EXIT_ACKNOWLEDGEMENT;

import modulo.model.Model;

/**
 * Terminates the program.
 */
public class ExitCommand extends Command {

    public static final String COMMAND_WORD = "exit";


    @Override
    public CommandResult execute(Model model) {
        return new CommandResult(MESSAGE_EXIT_ACKNOWLEDGEMENT, false, true, false, false, null, null);
    }

}
