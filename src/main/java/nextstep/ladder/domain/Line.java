package nextstep.ladder.domain;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import nextstep.ladder.domain.lines.position.Height;

public class Line {

    public static final String LINE_LENGTH_DIFFERENCE_EXCEPTION = "라인들의 길이가 다릅니다!";
    public static final String HORIZONTAL_LINE_OVERLAPPING_EXCEPTION = "사다리의 수평선이 서로 겹칩니다.";
    static final String LINE_INDEX_OUT_OF_RANGE_EXCEPTION = "라인의 범위를 넘어섰습니다.";
    private final List<Point> points;

    private Line(List<Point> points) {
        this.points = points;
    }

    public static Line createLineWithPoints(List<Point> points) {
        return new Line(points);
    }

    public static Line createLineWithPointStatus(List<Boolean> pointStatus) {
        return new Line(createPoints(pointStatus));
    }

    private static List<Point> createPoints(List<Boolean> pointStatus) {
        return pointStatus.stream().map(Point::valueOf).collect(Collectors.toList());
    }

    public Point horizontalLine(int height) {
        validateLineRange(height);
        return points.get(height);
    }

    private void validateLineRange(int height) {
        if (outOfRange(height)) {
            throw new ArrayIndexOutOfBoundsException(LINE_INDEX_OUT_OF_RANGE_EXCEPTION);
        }
    }

    private boolean outOfRange(int height) {
        return height >= this.points.size();
    }

    public void validateSameSizeAs(Line line) {
        if (isSameSize(line)) {
            throw new IllegalStateException(LINE_LENGTH_DIFFERENCE_EXCEPTION);
        }
    }

    private boolean isSameSize(Line line) {
        return this.points.size() != line.points.size();
    }

    public void isOverlapping(Line line) {
        int bound = this.points.size();
        for (int point = 0; point < bound; point++) {
            validateOverlapping(line, point);
        }
    }

    private void validateOverlapping(Line line, int point) {
        if (isOverlapping(line, point)) {
            throw new IllegalStateException(HORIZONTAL_LINE_OVERLAPPING_EXCEPTION);
        }
    }

    private boolean isOverlapping(Line otherLine, int pointIndex) {
        return getPoint(pointIndex).isOverlapping(otherLine.getPoint(pointIndex));
    }

    private Point getPoint(int point) {
        return this.points.get(point);
    }

    public int getMaxHeight() {
        return this.points.size();
    }

    public boolean isEmptyLine() {
        return this.points.isEmpty();
    }

    public boolean ableToMoveSideWay(int pointIndex) {
        return points.get(pointIndex).movable();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Line line = (Line) o;
        return Objects.equals(points, line.points);
    }

    @Override
    public int hashCode() {
        return Objects.hash(points);
    }

    @Override
    public String toString() {
        return "Line{" +
                "points=" + points +
                '}';
    }
}
