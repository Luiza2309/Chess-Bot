package src;

import java.util.ArrayList;

public class Knight extends APiece {
    public Knight(PlaySide side) {
        super(side);
        super.value = 40;
        setType(Piece.KNIGHT);
    }

    public double[][] importanceW = {
            {-5,  -4,  -3,  -3,  -3,  -3, -4, -5},
            {-4,  -2, 0.5,   0, 0.5,   0, -2, -4},
            {-3,   0,   1, 1.5, 1.5,   1,  0, -3},
            {-3, 0.5, 1.5,   2,   2, 1.5,  0, -3},
            {-3, 0.5, 1.5,   2,   2, 1.5,  0, -3},
            {-3,   0,   1, 1.5, 1.5,   1,  0, -3},
            {-4,  -2, 0.5,   0, 0.5,   0, -2, -4},
            {-5,  -4,  -3,  -3,  -3,  -3, -4, -5}
    };

    public double[][] importanceB = {
            {-5, -4,  -3,  -3,  -3,  -3,  -4, -5},
            {-4, -2, 0.5,   0, 0.5,   0,  -2, -4},
            {-3,  0,   1, 1.5, 1.5,   1,   0, -3},
            {-3,  0, 1.5,   2,   2, 1.5, 0.5, -3},
            {-3,  0, 1.5,   2,   2, 1.5, 0.5, -3},
            {-3,  0,   1, 1.5, 1.5,   1,   0, -3},
            {-4, -2, 0.5,   0, 0.5,   0,  -2, -4},
            {-5, -4,  -3,  -3,  -3,  -3,  -4, -5}
    };


    @Override
    public double[][] getImportanceW() {
        return importanceW;
    }

    public double[][] getImportanceB() {
        return importanceB;
    }
    // cal -> sare
    @Override
    public ArrayList<Move> calculatePossibleMoves(String source, APiece[][] table) {
        ArrayList<Move> moves = new ArrayList<>();
        int[] src = translateMoveToIndex(source);
        String dest;

        if((src[0] - 1 >= 0) && (src[1] - 2 >= 0)) {
            if (table[src[0] - 1][src[1] - 2] == null) {
                dest = translateIndexToMove(new int[]{src[0] - 1, src[1] - 2});
                moves.add(Move.moveTo(source, dest));
            } else if (table[src[0] - 1][src[1] - 2].getSide() != this.getSide()) {
                dest = translateIndexToMove(new int[]{src[0] - 1, src[1] - 2});
                Move m = Move.moveTo(source, dest);
                m.valueCapt = table[src[0] - 1][src[1] - 2].value;
                m.setCapture(true);
                moves.add(m);
            }
        }

        if((src[0] - 2 >= 0) && (src[1] - 1 >= 0)) {
            if (table[src[0] - 2][src[1] - 1] == null) {
                dest = translateIndexToMove(new int[]{src[0] - 2, src[1] - 1});
                moves.add(Move.moveTo(source, dest));
            } else if (table[src[0] - 2][src[1] - 1].getSide() != this.getSide()) {
                dest = translateIndexToMove(new int[]{src[0] - 2, src[1] - 1});
                Move m = Move.moveTo(source, dest);
                m.valueCapt = table[src[0] - 2][src[1] - 1].value;
                m.setCapture(true);
                moves.add(m);
            }
        }

        if((src[0] - 1 >= 0) && (src[1] + 2 < 8)) {
            if (table[src[0] - 1][src[1] + 2] == null) {
                dest = translateIndexToMove(new int[]{src[0] - 1, src[1] + 2});
                moves.add(Move.moveTo(source, dest));
            } else if (table[src[0] - 1][src[1] + 2].getSide() != this.getSide()) {
                dest = translateIndexToMove(new int[]{src[0] - 1, src[1] + 2});
                Move m = Move.moveTo(source, dest);
                m.valueCapt = table[src[0] - 1][src[1] + 2].value;
                m.setCapture(true);
                moves.add(m);
            }
        }

        if((src[0] - 2 >= 0) && (src[1] + 1 < 8)) {
            if (table[src[0] - 2][src[1] + 1] == null || table[src[0] - 2][src[1] + 1].getSide() != this.getSide()) {
                dest = translateIndexToMove(new int[]{src[0] - 2, src[1] + 1});
                moves.add(Move.moveTo(source, dest));
            }
        }
        //////////////////

        if((src[0] + 1 < 8) && (src[1] - 2 >= 0)) {
            if (table[src[0] + 1][src[1] - 2] == null) {
                dest = translateIndexToMove(new int[]{src[0] + 1, src[1] - 2});
                moves.add(Move.moveTo(source, dest));
            } else if (table[src[0] + 1][src[1] - 2].getSide() != this.getSide()) {
                dest = translateIndexToMove(new int[]{src[0] + 1, src[1] - 2});
                Move m = Move.moveTo(source, dest);
                m.valueCapt = table[src[0] + 1][src[1] - 2].value;
                m.setCapture(true);
                moves.add(m);
            }
        }

        if((src[0] + 2 < 8) && (src[1] - 1 >= 0)) {
            if (table[src[0] + 2][src[1] - 1] == null) {
                dest = translateIndexToMove(new int[]{src[0] + 2, src[1] - 1});
                moves.add(Move.moveTo(source, dest));
            } else if (table[src[0] + 2][src[1] - 1].getSide() != this.getSide()) {
                dest = translateIndexToMove(new int[]{src[0] + 2, src[1] - 1});
                Move m = Move.moveTo(source, dest);
                m.valueCapt = table[src[0] + 2][src[1] - 1].value;
                m.setCapture(true);
                moves.add(m);
            }
        }


        if((src[0] + 1 < 8) && (src[1] + 2 < 8)) {
            if (table[src[0] + 1][src[1] + 2] == null) {
                dest = translateIndexToMove(new int[]{src[0] + 1, src[1] + 2});
                moves.add(Move.moveTo(source, dest));
            } else if (table[src[0] + 1][src[1] + 2].getSide() != this.getSide()) {
                dest = translateIndexToMove(new int[]{src[0] + 1, src[1] + 2});
                Move m = Move.moveTo(source, dest);
                m.valueCapt = table[src[0] + 1][src[1] + 2].value;
                m.setCapture(true);
                moves.add(m);
            }
        }

        if((src[0] + 2 < 8) && (src[1] + 1 < 8)) {
            if (table[src[0] + 2][src[1] + 1] == null) {
                dest = translateIndexToMove(new int[]{src[0] + 2, src[1] + 1});
                moves.add(Move.moveTo(source, dest));
            } else if (table[src[0] + 2][src[1] + 1].getSide() != this.getSide()) {
                dest = translateIndexToMove(new int[]{src[0] + 2, src[1] + 1});
                Move m = Move.moveTo(source, dest);
                m.valueCapt = table[src[0] + 2][src[1] + 1].value;
                m.setCapture(true);
                moves.add(m);
            }
        }

        return moves;
    }
}
