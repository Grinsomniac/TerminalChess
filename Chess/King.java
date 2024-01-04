package chess;

import java.util.*;

public class King extends Piece {
    public King(PieceFile pieceFile, int pieceRank, boolean isWhite) {
        super(pieceFile, pieceRank, isWhite);
    }

    @Override
    public PieceType getWhitePieceType() {
        return PieceType.WK;
    }

    @Override
    public PieceType getBlackPieceType() {
        return PieceType.BK;
    }

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
    public boolean isMoveValid(int newRank, ReturnPiece.PieceFile newFile, ArrayList<ReturnPiece> piecesOnBoard,
            boolean playerWhite, char promotionPiece) {
        int rankDifference = Math.abs(newRank - pieceRank);
        int fileDifference = Math.abs(newFile.ordinal() - pieceFile.ordinal());

        if (rankDifference == 0 && fileDifference == 2) {
            if (newFile == PieceFile.g && pieceFile == PieceFile.e) {

            } else if (newFile == PieceFile.c && pieceFile == PieceFile.e) {

            }
        }

        if (rankDifference <= 1 && fileDifference <= 1) {
            for (ReturnPiece piece : piecesOnBoard) {
                if (piece.pieceRank == newRank
                        && piece.pieceFile.toString().charAt(0) == newFile.toString().charAt(0)) {

                    if (piece.pieceType.toString().charAt(0) == 'W' && !playerWhite) {
                        if (piece.pieceType.toString().charAt(1) != 'K') {
                            capture(pieceFile, pieceRank, newFile, newRank, piecesOnBoard);
                        }
                        return true;
                    } else {
                        if (piece.pieceType.toString().charAt(0) == 'B' && playerWhite) {
                            if (piece.pieceType.toString().charAt(1) != 'K') {
                                capture(pieceFile, pieceRank, newFile, newRank, piecesOnBoard);
                            }
                            return true;
                        } else {
                            return false;
                        }
                    }
                }
            }
            return true;
        }
        return false;
    }

    public boolean isKingInCheck(ArrayList<ReturnPiece> piecesOnBoard) {

        int piecesChecking = 0;
        for (ReturnPiece piece : piecesOnBoard) {

            if (piece.pieceType.toString().charAt(1) == 'P') {
                Pawn castedPawnPiece = (Pawn) piece;

                if (castedPawnPiece.isMoveValid(pieceRank, pieceFile, piecesOnBoard, castedPawnPiece.isWhite, ' ')) {
                    System.out.println(
                            "check from pawn" + castedPawnPiece.pieceFile.toString() + "" + castedPawnPiece.pieceRank);
                    piecesChecking++;
                }
            }

            if (piece.pieceType.toString().charAt(1) == 'Q') {
                Queen castedQueenPiece = (Queen) piece;

                if (castedQueenPiece.isMoveValid(pieceRank, pieceFile, piecesOnBoard, castedQueenPiece.isWhite, ' ')) {
                    System.out.println("check from queen" + castedQueenPiece.pieceFile.toString() + ""
                            + castedQueenPiece.pieceRank);
                    piecesChecking++;
                }
            }

            if (piece.pieceType.toString().charAt(1) == 'B') {
                Bishop castedBishopPiece = (Bishop) piece;

                if (castedBishopPiece.isMoveValid(pieceRank, pieceFile, piecesOnBoard, castedBishopPiece.isWhite,
                        ' ')) {
                    System.out.println("check from bishop" + castedBishopPiece.pieceFile.toString() + ""
                            + castedBishopPiece.pieceRank);
                    piecesChecking++;

                }

            }

            if (piece.pieceType.toString().charAt(1) == 'R') {
                Rook castedRookPiece = (Rook) piece;

                if (castedRookPiece.isMoveValid(pieceRank, pieceFile, piecesOnBoard, castedRookPiece.isWhite, ' ')) {
                    System.out.println(
                            "check from rook" + castedRookPiece.pieceFile.toString() + "" + castedRookPiece.pieceRank);
                    piecesChecking++;

                }

            }

            if (piece.pieceType.toString().charAt(1) == 'N') {
                Knight castedKnightPiece = (Knight) piece;

                if (castedKnightPiece.isMoveValid(pieceRank, pieceFile, piecesOnBoard, castedKnightPiece.isWhite,
                        ' ')) {
                    System.out.println("check from knight" + castedKnightPiece.pieceFile.toString() + ""
                            + castedKnightPiece.pieceRank);
                    piecesChecking++;

                }
            }
        }

        if (piecesChecking > 0) {
            System.out.println("in check from" + piecesChecking + "pieces");
            return true;
        } else {

            return false;
        }

    }
}
