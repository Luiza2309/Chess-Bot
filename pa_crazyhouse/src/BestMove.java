package src;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class BestMove {
    public static final int MAX_DEPTH = 3;
    public PlaySide side, enemySide;
    public Move getBestMove(Bot bot) {
        double maxScore = Double.MIN_VALUE;
        Move bestMove = null;
        side = bot.getMySide();
       Bot botulet = null;
       try {
           botulet = (Bot)bot.clone();
       } catch (CloneNotSupportedException e) {
           throw new RuntimeException(e);
       }

        if (side.equals(PlaySide.WHITE))
            enemySide = PlaySide.BLACK;
        else enemySide = PlaySide.WHITE;

        for(Move move: bot.calculateAllMoves()) {
            botulet.recordMove(move, side);
            double score = minimax(botulet, false, Double.MIN_VALUE, Double.MAX_VALUE, MAX_DEPTH, enemySide);
            botulet.undoLastMove();
          
            if(score > maxScore) {
                maxScore = score;
                bestMove = move;
            }
        }

        return bestMove;
    }

    public double minimax(Bot bot, boolean whoPlays, double alpha, double beta, int depth, PlaySide who) {
        if(depth == 0 || bot.isOverNow()) {
            return calculateSum(bot); // calculam sansele de castig
        }

       Bot botulet = null;
       try {
           botulet = (Bot)bot.clone();
       } catch (CloneNotSupportedException e) {
           throw new RuntimeException(e);
       }

        if(whoPlays) {
            double maxScore = Double.MIN_VALUE;

            bot.setSide(side);

            for(Move move: bot.calculateAllMoves()) {
               botulet.recordMove(move, side);
                double score = minimax(botulet, false, alpha, beta, depth -1, enemySide);
                botulet.undoLastMove();
              
                maxScore = Math.max(maxScore, score);
                alpha = Math.max(alpha, score);

                if(alpha >= beta) {
                    break;
                }
            }

            return maxScore;
        } else {
            double minScore = Double.MAX_VALUE;

            bot.setSide(enemySide);

            for(Move move: bot.calculateAllMoves()) {
               botulet.recordMove(move, enemySide);
                double score = minimax(botulet, true, alpha, beta, depth -1, side);
                botulet.undoLastMove();

                minScore = Math.min(minScore, score);
                beta = Math.min(beta, score);

                if(alpha >= beta) {
                    break;
                }
            }

            return minScore;
        }
    }

    public double calculateSum(Bot bot) {
        double score = 0;

        // ce piese avem, cate miscari au si unde sunt
        score += evaluatePieces(bot);

        // TODO: daca regele intra in sah => NU
        //        score -= 1000;

        return score;
    }

    private double evaluatePieces(Bot bot) {
        double sum = 0;

        // evaluarea pieselor pe tabla si mobilitate
        // primul numar este importanta unei piese
        // al doilea este importanta mobilitatii (1 punct pentru fiecare miscare)
        // al treilea este importanta pozitiei pe tabla
        // fiecare piesa are locuri favorabile si mai putin favorabile pe tabla
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (bot.getTable()[i][j] != null) {
                    String source = APiece.createDestination(i, j);
                    APiece p = bot.getTable()[i][j];
                    if (p.getType() == Piece.PAWN) {
                        if (p.getSide() == bot.getMySide()) {
                            if(p.getSide() == PlaySide.BLACK) {
                                sum = sum + 10 + p.calculatePossibleMoves(source, bot.getTable()).size() + p.getImportanceB()[i][j];
                            } else {
                                sum = sum + 10 + p.calculatePossibleMoves(source, bot.getTable()).size() + p.getImportanceW()[i][j];
                            }
                        } else  {
                            if(p.getSide() == PlaySide.BLACK) {
                                sum = sum - 10 - p.calculatePossibleMoves(source, bot.getTable()).size() - p.getImportanceB()[i][j];
                            } else {
                                sum = sum - 10 - p.calculatePossibleMoves(source, bot.getTable()).size() - p.getImportanceW()[i][j];
                            }
                        }
                    } else if (p.getType() == Piece.BISHOP) {
                        if (p.getSide() == bot.getMySide()) {
                            if(p.getSide() == PlaySide.BLACK) {
                                sum = sum + 30 + p.calculatePossibleMoves(source, bot.getTable()).size() + p.getImportanceB()[i][j];
                            } else {
                                sum = sum + 30 + p.calculatePossibleMoves(source, bot.getTable()).size() + p.getImportanceW()[i][j];
                            }
                        } else {
                            if(p.getSide() == PlaySide.BLACK) {
                                sum = sum - 30 - p.calculatePossibleMoves(source, bot.getTable()).size() - p.getImportanceB()[i][j];
                            } else {
                                sum = sum - 30 - p.calculatePossibleMoves(source, bot.getTable()).size() - p.getImportanceW()[i][j];
                            }
                        }
                    } else if (p.getType() == Piece.KNIGHT) {
                        if (p.getSide() == bot.getMySide()) {
                            if(p.getSide() == PlaySide.BLACK) {
                                sum = sum + 40 + p.calculatePossibleMoves(source, bot.getTable()).size() + p.getImportanceB()[i][j];
                            } else {
                                sum = sum + 40 + p.calculatePossibleMoves(source, bot.getTable()).size() + p.getImportanceW()[i][j];
                            }
                        } else {
                            if(p.getSide() == PlaySide.BLACK) {
                                sum = sum - 40 - p.calculatePossibleMoves(source, bot.getTable()).size() - p.getImportanceB()[i][j];
                            } else {
                                sum = sum - 40 - p.calculatePossibleMoves(source, bot.getTable()).size() - p.getImportanceW()[i][j];
                            }

                        }
                    } else if (p.getType() == Piece.ROOK) {
                        if (p.getSide() == bot.getMySide()) {
                            if(p.getSide() == PlaySide.BLACK) {
                                sum = sum + 40 + p.calculatePossibleMoves(source, bot.getTable()).size() + p.getImportanceB()[i][j];
                            } else {
                                sum = sum + 40 + p.calculatePossibleMoves(source, bot.getTable()).size() + p.getImportanceW()[i][j];
                            }
                        } else {
                            if(p.getSide() == PlaySide.BLACK) {
                                sum = sum - 40 - p.calculatePossibleMoves(source, bot.getTable()).size() - p.getImportanceB()[i][j];
                            } else {
                                sum = sum - 40 - p.calculatePossibleMoves(source, bot.getTable()).size() - p.getImportanceW()[i][j];
                            }
                        }
                    } else if (p.getType() == Piece.QUEEN) {
                        if (p.getSide() == bot.getMySide()) {
                            if(p.getSide() == PlaySide.BLACK) {
                                sum = sum + 80 + p.calculatePossibleMoves(source, bot.getTable()).size() + p.getImportanceB()[i][j];
                            } else {
                                sum = sum + 80 + p.calculatePossibleMoves(source, bot.getTable()).size() + p.getImportanceW()[i][j];
                            }
                        } else {
                            if(p.getSide() == PlaySide.BLACK) {
                                sum = sum - 80 - p.calculatePossibleMoves(source, bot.getTable()).size() - p.getImportanceB()[i][j];
                            } else {
                                sum = sum - 80 - p.calculatePossibleMoves(source, bot.getTable()).size() - p.getImportanceW()[i][j];
                            }
                        }
                    }
                }
            }
        }

        // evaluarea pieselor din mana
        // pentru piesele negre capturate
        for (int i = 0; i < bot.getCapturedBlack().size(); i++) {
            APiece p = bot.getCapturedBlack().get(i);
            if (p.getType() == Piece.PAWN) {
                if (p.getSide() == bot.getMySide()) sum += 20;
                else sum -= 20;
            } else if (p.getType() == Piece.BISHOP) {
                if (p.getSide() == bot.getMySide()) sum += 40;
                else sum -= 40;
            } else if (p.getType() == Piece.KNIGHT) {
                if (p.getSide() == bot.getMySide()) sum += 70;
                else sum -= 70;
            } else if (p.getType() == Piece.ROOK) {
                if (p.getSide() == bot.getMySide()) sum += 50;
                else sum -= 50;
            } else if (p.getType() == Piece.QUEEN) {
                if (p.getSide() == bot.getMySide()) sum += 100;
                else sum -= 100;
            }
        }

        // pentru piesele albe capturate
        for (int i = 0; i < bot.getCapturedWhite().size(); i++) {
            APiece p = bot.getCapturedWhite().get(i);
            if (p.getType() == Piece.PAWN) {
                if (p.getSide() == bot.getMySide()) sum += 20;
                else sum -= 20;
            } else if (p.getType() == Piece.BISHOP) {
                if (p.getSide() == bot.getMySide()) sum += 40;
                else sum -= 40;
            } else if (p.getType() == Piece.KNIGHT) {
                if (p.getSide() == bot.getMySide()) sum += 70;
                else sum -= 70;
            } else if (p.getType() == Piece.ROOK) {
                if (p.getSide() == bot.getMySide()) sum += 50;
                else sum -= 50;
            } else if (p.getType() == Piece.QUEEN) {
                if (p.getSide() == bot.getMySide()) sum += 100;
                else sum -= 100;
            }
        }

        return sum;
    }

}
