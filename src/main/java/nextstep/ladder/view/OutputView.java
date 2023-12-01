package nextstep.ladder.view;

import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import nextstep.ladder.domain.Ladder;

public class OutputView {

    public static final String EXECUTION_RESULT_MESSAGE = "실행결과\n";
    public static final String NONE_NAMES_OF_PARTICIPANTS_EXCEPTION = "참가자들의 이름들이 존재하지 않습니다.";

    public void printLadderExecutionResult(List<String> namesOfParticipants, Ladder ladder) {
        printExecutionMessage();
        printParticipants(namesOfParticipants);
        int maxHeight = ladder.getLadderLines().getLines().get(0).getMaxHeight();
        IntStream.range(0, maxHeight)
                .forEachOrdered(currentHeight -> {
                    printLadderOfCurrentHeight(namesOfParticipants, ladder, currentHeight);
                });
    }

    private void printExecutionMessage() {
        System.out.println(EXECUTION_RESULT_MESSAGE);
    }

    private void printParticipants(List<String> namesOfParticipants) {
        for (String participantName : namesOfParticipants) {
            System.out.print(createCompartmentsBetweenParticipants(participantName));
        }
        System.out.println();
    }

    private String createCompartmentsBetweenParticipants(String participantName) {
        if (participantName.length() == 5) {
            return " " + participantName;
        }

        String tempName = "     " + participantName + " ";
        return tempName.substring(tempName.length() - 6);
    }

    private void printLadderOfCurrentHeight(List<String> namesOfParticipants, Ladder ladder, int presentHeight) {
        printCompartmentsOfCurrentHeight(namesOfParticipants.get(0), ladder.getWidth());
        printPureLadderOfCurrentHeight(namesOfParticipants, ladder, presentHeight);
    }

    private void printCompartmentsOfCurrentHeight(String participantName, int width) {
        Optional.ofNullable(participantName).ifPresentOrElse(
                participantsName -> {
                    System.out.print(" ".repeat(width));
                },
                () -> {
                    throw new NullPointerException(NONE_NAMES_OF_PARTICIPANTS_EXCEPTION);
                }
        );
    }

    private void printPureLadderOfCurrentHeight(List<String> namesOfParticipants, Ladder ladder,
                                                int currentHeight) {
        IntStream.range(0, namesOfParticipants.size() - 1).forEachOrdered(currentPointIndex -> {
            printOnePieceOfLine();
            printHorizontalLine(ladder, currentHeight, currentPointIndex);
        });
        printLastPieceOfLine();
    }

    private void printOnePieceOfLine() {
        System.out.print("|");
    }

    private void printHorizontalLine(Ladder ladder, int presentHeight, int currentPointIndex) {
        if (hasHorizontalLine(ladder, presentHeight, currentPointIndex)) {
            System.out.print("-".repeat(ladder.getWidth()));
            return;
        }
        System.out.print(" ".repeat(ladder.getWidth()));
    }

    private boolean hasHorizontalLine(Ladder ladder, int presentHeight, int currentPointIndex) {
        return ladder.getLadderLines().getLines().get(currentPointIndex).hasHorizontalLine(presentHeight);
    }

    private void printLastPieceOfLine() {
        printOnePieceOfLine();
        System.out.println();
    }
}
