package chess;

import java.util.*;

public class Pawn extends Piece {
    public Pawn(PieceFile pieceFile, int pieceRank, boolean isWhite) {
        super(pieceFile, pieceRank, isWhite);
    }

    public static boolean enPassant = false;

    @Override
    public PieceType getWhitePieceType() {
        return PieceType.WP;
    }

    @Override
    public PieceType getBlackPieceType() {
        return PieceType.BP;
    }

    @Override
    public boolean getisWhite() {
        return isWhite;
    }

    @Override
    public ReturnPiece.PieceFile getPieceFile() {
        return pieceFile;
    }

    @Override
    public int getPieceRank() {
        return pieceRank;
    }

    @Override
    public boolean isMoveValid(int newRank, PieceFile newFile, ArrayList<ReturnPiece> piecesOnBoard,
            boolean playerWhite, char promotionPiece) {
        if (newRank == pieceRank && newFile == pieceFile) {
            return false;
        }
        int rankDifference;
        int fileDifference;
        if (getisWhite()) {
            rankDifference = (newRank - pieceRank);
            fileDifference = Math.abs(newFile.ordinal() - pieceFile.ordinal());
        } else {
            rankDifference = (pieceRank - newRank);
            fileDifference = Math.abs(newFile.ordinal() - pieceFile.ordinal());
        }

        int rankDirection = Integer.compare(newRank, pieceRank);
        int fileDirection = Integer.compare(newFile.ordinal(), pieceFile.ordinal());

        if (rankDifference == 1 && fileDifference == 0) {
            for (ReturnPiece piece : piecesOnBoard) {
                if (piece.pieceRank == newRank && piece.pieceFile == newFile) {
                    return false;
                }
            }
            pawnPromo(newRank, newFile, piecesOnBoard, playerWhite, promotionPiece);
            enPassant = false;
            pieceMoveCount++;
            return true;
        } else if (rankDifference == 2 && fileDifference == 0
                && ((isWhite && pieceRank == 2) || (!isWhite && pieceRank == 7)) && fileDirection == 0) {
            int intermediateRank = pieceRank + rankDirection;
            for (ReturnPiece piece : piecesOnBoard) {
                if ((piece.pieceRank == intermediateRank && piece.pieceFile == pieceFile)
                        || (piece.pieceRank == newRank && piece.pieceFile == newFile)) {
                    return false;
                }
            }
            pieceMoveCount++;
            if (pieceMoveCount == 1) {
                enPassant = true;
            }
            return true;
        } else if (rankDifference == 1 && fileDifference == 1
                && pieceFile.ordinal() + fileDirection == newFile.ordinal()) {

            if ((enPassant && (playerWhite && pieceRank == 5)) || (enPassant && (!playerWhite && pieceRank == 4))) {
                System.out.println(enPassant);
                System.out.println("passed check for enpassant");

                int enPassantRank = (playerWhite) ? (newRank - 1) : (newRank + 1);
                int enPassantFile = newFile.ordinal();

                for (ReturnPiece piece : piecesOnBoard) {
                    if (piece.pieceRank == enPassantRank && piece.pieceFile == PieceFile.values()[enPassantFile]) {

                        enPassantCapture(pieceFile, pieceRank, newFile, newRank, piecesOnBoard, playerWhite);
                        pieceMoveCount++;
                        return true;
                    }
                }
            }

            for (ReturnPiece piece : piecesOnBoard) {
                if (piece.pieceRank == newRank && piece.pieceFile == newFile) {

                    if (piece.pieceType.toString().charAt(0) == 'W' && !playerWhite) {
                        if (piece.pieceType.toString().charAt(1) != 'K') {
                            capture(pieceFile, pieceRank, newFile, newRank, piecesOnBoard);
                        }

                        pawnPromo(newRank, newFile, piecesOnBoard, playerWhite, promotionPiece);

                        pieceMoveCount++;

                        return true;
                    } else if (piece.pieceType.toString().charAt(0) == 'B' && playerWhite) {
                        if (piece.pieceType.toString().charAt(1) != 'K') {
                            capture(pieceFile, pieceRank, newFile, newRank, piecesOnBoard);
                        }

                        pawnPromo(newRank, newFile, piecesOnBoard, playerWhite, promotionPiece);
                        pieceMoveCount++;
                        return true;
                    } else {
                        return false;
                    }
                }
            }
        }

        return false;
    }

    public void enPassantCapture(ReturnPiece.PieceFile movingFile, int movingRank, ReturnPiece.PieceFile takeFile,
            int takeRank, ArrayList<ReturnPiece> piecesOnBoard, boolean playerWhite) {

        for (int i = 0; i < piecesOnBoard.size(); i++) {
            if (playerWhite) {
                if (piecesOnBoard.get(i).pieceFile.toString().charAt(0) == takeFile.toString().charAt(0)
                        && piecesOnBoard.get(i).pieceRank == takeRank - 1) {
                    piecesOnBoard.remove(i);
                }
            }
            if (!playerWhite) {
                if (piecesOnBoard.get(i).pieceFile.toString().charAt(0) == takeFile.toString().charAt(0)
                        && piecesOnBoard.get(i).pieceRank == takeRank + 1) {
                    piecesOnBoard.remove(i);
                }
            }
        }
    }

    public void pawnPromo(int newRank, PieceFile newFile, ArrayList<ReturnPiece> piecesOnBoard, boolean playerWhite,
            char promotionPiece) {

        if ((newRank == 1 && !playerWhite) || (newRank == 8 && playerWhite)) {

            piecesOnBoard.remove(this);
            Piece promotedPiece;

            switch (promotionPiece) {
                case 'b':
                    promotedPiece = new Bishop(newFile, newRank, playerWhite);
                    break;
                case 'n':
                    promotedPiece = new Knight(newFile, newRank, playerWhite);
                    break;
                case 'r':
                    promotedPiece = new Rook(newFile, newRank, playerWhite);
                    break;
                case 'q':
                    promotedPiece = new Queen(newFile, newRank, playerWhite);
                    break;
                case ' ':
                    promotedPiece = new Queen(newFile, newRank, playerWhite);
                    break;
                default:
                    promotedPiece = new Queen(newFile, newRank, playerWhite);
                    break;
            }
            piecesOnBoard.add(promotedPiece);
        }
    }

}
