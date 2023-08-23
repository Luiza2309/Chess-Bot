package src;

import java.util.ArrayList;

public class King extends APiece {
    public boolean moved = false;

    public King(PlaySide side) {
        super(side);
        super.value = 0;
        setType(Piece.KING);
    }

    public double[][] importanceW = {
            {2, 2, -1, -2, -3, -3, -3, -3},
            {3, 2, -2, -3, -4, -4, -4, -4},
            {1, 0, -2, -3, -4, -4, -4, -4},
            {0, 0, -2, -4, -5, -5, -5, -5},
            {0, 0, -2, -4, -5, -5, -5, -5},
            {1, 0, -2, -3, -4, -4, -4, -4},
            {2, 2, -2, -3, -4, -4, -4, -4},
            {2, 2, -1, -2, -3, -3, -3, -3}
    };

    public double[][] importanceB = {
            {-3, -3, -3, -3, -2, -1, 2, 2},
            {-4, -4, -4, -4, -3, -2, 2, 3},
            {-4, -4, -4, -4, -3, -2, 0, 1},
            {-5, -5, -5, -5, -4, -2, 0, 0},
            {-5, -5, -5, -5, -4, -2, 0, 0},
            {-4, -4, -4, -4, -3, -2, 0, 1},
            {-4, -4, -4, -4, -3, -2, 2, 2},
            {-3, -3, -3, -3, -2, -1, 2, 2}
    };

    @Override
    public double[][] getImportanceW() {
        return importanceW;
    }

    public double[][] getImportanceB() {
        return importanceB;
    }

    @Override
    public ArrayList<Move> calculatePossibleMoves(String source, APiece[][] table) {
        ArrayList<Move> moves = new ArrayList<>();
        int[] src = translateMoveToIndex(source);

        for (int i = src[0] - 1; i <= src[0] + 1; i++) {
            if(i >= 0 && i < 8) {
                for (int j = src[1] - 1; j <= src[1] + 1; j++) {
                    if (j >= 0 && j < 8) {
                        if (src[0] == i && src[1] == j)
                            continue;
                        if (table[i][j] == null) {
                            moves.add(Move.moveTo(source, translateIndexToMove(new int[]{i, j})));
                        } else if (table[i][j].getSide() != this.getSide()) {
                            Move m = Move.moveTo(source, translateIndexToMove(new int[]{i, j}));
                            m.valueCapt = table[i][j].value;
                            m.setCapture(true);
                            moves.add(m);
                        }
                    }
                }
            }
        }

        return moves;
    }
}
