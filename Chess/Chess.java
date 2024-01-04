//Ian Underwood
//Jason John

package chess;

import java.util.*;

///////////////////////     NO CHANGES     /////////////////////////////////
class ReturnPiece {
	static enum PieceType {
		WP, WR, WN, WB, WQ, WK,
		BP, BR, BN, BB, BK, BQ
	};

	static enum PieceFile {
		a, b, c, d, e, f, g, h
	};

	PieceType pieceType;
	PieceFile pieceFile;
	int pieceRank; // 1..8

	public String toString() {
		return "" + pieceFile + pieceRank + ":" + pieceType;
	}

	public boolean equals(Object other) {
		if (other == null || !(other instanceof ReturnPiece)) {
			return false;
		}
		ReturnPiece otherPiece = (ReturnPiece) other;
		return pieceType == otherPiece.pieceType &&
				pieceFile == otherPiece.pieceFile &&
				pieceRank == otherPiece.pieceRank;
	}
}

/////////////////////// NO CHANGES /////////////////////////////////
class ReturnPlay {
	enum Message {
		ILLEGAL_MOVE, DRAW,
		RESIGN_BLACK_WINS, RESIGN_WHITE_WINS,
		CHECK, CHECKMATE_BLACK_WINS, CHECKMATE_WHITE_WINS,
		STALEMATE
	};

	ArrayList<ReturnPiece> piecesOnBoard;
	Message message;
}

/////////////////////// ADD TO CHESS CLASS ONLY //////////////////////
public class Chess {

	enum Player {
		white, black
	} // No Touch

	public static Player currentPlayer;
	public static String whitePrompt = "\nWhite's Move: ";
	public static String blackPrompt = "\nBlack's Move: ";
	public static ArrayList<ReturnPiece> piecesOnBoard; //= new ArrayList<>();
	public static ReturnPlay returnPlay = new ReturnPlay();
	public static int moveCount;
	public static String[] parts;
	public static String source;
	public static String destination;
	public static ReturnPiece targetPiece = null;
	public static boolean playerWhite;
	public static char promotionPiece = ' ';
	public static boolean isDraw;

	public static ReturnPlay play(String move) {

		if (resignCheck(move)) {
			return returnPlay;
		}

		parseMove(move);

		if (!validateMove(targetPiece)) {

			returnPlay.message = ReturnPlay.Message.ILLEGAL_MOVE;
			returnPlay.piecesOnBoard = piecesOnBoard;
			return returnPlay;
		}
		returnPlay.message = null;
		movePiece(targetPiece, destination);

		if (isDraw) {
			returnPlay.message = ReturnPlay.Message.DRAW;
			returnPlay.piecesOnBoard = piecesOnBoard;
			return returnPlay;
		}

		for (ReturnPiece piece : piecesOnBoard) {
			if (piece.pieceType.toString().charAt(1) == 'K') {
				King king = (King) piece;
				if (king.isKingInCheck(piecesOnBoard)) {
					returnPlay.message = ReturnPlay.Message.CHECK;

				}
			}
		}

		switchTurn();

		returnPlay.piecesOnBoard = piecesOnBoard;
		return returnPlay;

	}

	public static Boolean resignCheck(String move) {

		if (move.trim().toLowerCase().equals("resign")) {
			if (currentPlayer == Player.black) {
				returnPlay.message = ReturnPlay.Message.RESIGN_WHITE_WINS;
				returnPlay.piecesOnBoard = piecesOnBoard;
				return true;
			} else {
				returnPlay.message = ReturnPlay.Message.RESIGN_BLACK_WINS;
				returnPlay.piecesOnBoard = piecesOnBoard;
				return true;
			}
		}
		return false;
	}

	public static void parseMove(String move) {

		move = move.trim().toLowerCase();
		parts = move.split(" ");

		if (parts.length == 2) {
			source = parts[0];
			destination = parts[1];
		} else if (parts.length == 3) {
			source = parts[0];
			destination = parts[1];
			if (parts[2].charAt(0) != 'd') {
				promotionPiece = parts[2].charAt(0);
			} else {
				isDraw = true;
			}
		}

		searchPieces(piecesOnBoard, source);
	}

	public static void searchPieces(ArrayList<ReturnPiece> piecesOnBoard, String source) {

		char targetFile = source.charAt(0);
		int targetRank = (source.charAt(1) - 48);

		for (ReturnPiece piece : piecesOnBoard) {
			if (piece.pieceFile.name().charAt(0) == targetFile && piece.pieceRank == targetRank) {

				targetPiece = piece;
				break;
			}
		}
	}

	public static Boolean validateMove(ReturnPiece targetPiece) {

		char targetFile = destination.charAt(0);
		int targetRank = (destination.charAt(1) - 48);
		boolean colorCheck = false;

		if ((targetPiece.pieceType.toString().charAt(0) == 'W' && currentPlayer == Player.white)) {
			colorCheck = true;
		} else {
			if ((targetPiece.pieceType.toString().charAt(0) == 'B' && currentPlayer == Player.black))
				colorCheck = true;
		}

		if (!colorCheck) {
			return false;
		}

		if (targetPiece.pieceType.toString().charAt(1) == 'P') {
			Pawn castedPawnPiece = (Pawn) targetPiece;
			if (castedPawnPiece.isMoveValid(targetRank, ReturnPiece.PieceFile.valueOf(String.valueOf(targetFile)),
					piecesOnBoard, playerWhite, promotionPiece)) {
				return true;
			} else {
				return false;
			}
		}

		if (targetPiece.pieceType.toString().charAt(1) == 'R') {
			Rook castedRookPiece = (Rook) targetPiece;
			if (castedRookPiece.isMoveValid(targetRank, ReturnPiece.PieceFile.valueOf(String.valueOf(targetFile)),
					piecesOnBoard, playerWhite, promotionPiece)) {
				return true;
			} else {
				return false;
			}
		}

		if (targetPiece.pieceType.toString().charAt(1) == 'B') {
			Bishop castedBishopPiece = (Bishop) targetPiece;
			if (castedBishopPiece.isMoveValid(targetRank, ReturnPiece.PieceFile.valueOf(String.valueOf(targetFile)),
					piecesOnBoard, playerWhite, promotionPiece)) {
				return true;
			} else {
				return false;
			}
		}

		if (targetPiece.pieceType.toString().charAt(1) == 'N') {
			Knight castedKnightPiece = (Knight) targetPiece;
			if (castedKnightPiece.isMoveValid(targetRank, ReturnPiece.PieceFile.valueOf(String.valueOf(targetFile)),
					piecesOnBoard, playerWhite, promotionPiece)) {
				return true;
			} else {
				return false;
			}
		}

		if (targetPiece.pieceType.toString().charAt(1) == 'Q') {
			Queen castedQueenPiece = (Queen) targetPiece;
			if (castedQueenPiece.isMoveValid(targetRank, ReturnPiece.PieceFile.valueOf(String.valueOf(targetFile)),
					piecesOnBoard, playerWhite, promotionPiece)) {
				return true;
			} else {
				return false;
			}
		}

		if (targetPiece.pieceType.toString().charAt(1) == 'K') {
			King castedKingPiece = (King) targetPiece;
			if (castedKingPiece.isMoveValid(targetRank, ReturnPiece.PieceFile.valueOf(String.valueOf(targetFile)),
					piecesOnBoard, playerWhite, promotionPiece)) {
				return true;
			} else {
				return false;
			}
		}

		return false;

	}

	public static void movePiece(ReturnPiece targetPiece, String destination) {

		char targetFile = destination.charAt(0);
		int targetRank = (destination.charAt(1) - 48);

		targetPiece.pieceFile = ReturnPiece.PieceFile.valueOf(String.valueOf(targetFile));
		targetPiece.pieceRank = targetRank;

	}

	public static void switchTurn() {
		if (currentPlayer == Player.white) {
			currentPlayer = Player.black;
			playerWhite = false;
		} else {
			currentPlayer = Player.white;
			playerWhite = true;
		}
	}

	public static void start() {
		InitializeBoard();
		currentPlayer = Player.white;
		moveCount = 0;
		System.out.print(whitePrompt);
	}

	public static void InitializeBoard() {

		piecesOnBoard = new ArrayList();

		piecesOnBoard.add(new Rook(ReturnPiece.PieceFile.a, 1, true));
		piecesOnBoard.add(new Rook(ReturnPiece.PieceFile.h, 1, true));
		piecesOnBoard.add(new Knight(ReturnPiece.PieceFile.b, 1, true));
		piecesOnBoard.add(new Knight(ReturnPiece.PieceFile.g, 1, true));
		piecesOnBoard.add(new Bishop(ReturnPiece.PieceFile.c, 1, true));
		piecesOnBoard.add(new Bishop(ReturnPiece.PieceFile.f, 1, true));
		piecesOnBoard.add(new Queen(ReturnPiece.PieceFile.d, 1, true));
		piecesOnBoard.add(new King(ReturnPiece.PieceFile.e, 1, true));

		for (ReturnPiece.PieceFile file : ReturnPiece.PieceFile.values()) {
			piecesOnBoard.add(new Pawn(file, 2, true));
		}

		piecesOnBoard.add(new Rook(ReturnPiece.PieceFile.a, 8, false));
		piecesOnBoard.add(new Rook(ReturnPiece.PieceFile.h, 8, false));
		piecesOnBoard.add(new Knight(ReturnPiece.PieceFile.b, 8, false));
		piecesOnBoard.add(new Knight(ReturnPiece.PieceFile.g, 8, false));
		piecesOnBoard.add(new Bishop(ReturnPiece.PieceFile.c, 8, false));
		piecesOnBoard.add(new Bishop(ReturnPiece.PieceFile.f, 8, false));
		piecesOnBoard.add(new Queen(ReturnPiece.PieceFile.d, 8, false));
		piecesOnBoard.add(new King(ReturnPiece.PieceFile.e, 8, false));

		for (ReturnPiece.PieceFile file : ReturnPiece.PieceFile.values()) {
			piecesOnBoard.add(new Pawn(file, 7, false));
		}

		PlayChess.printBoard(piecesOnBoard);
	}
}
