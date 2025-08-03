package io.github.StardewValley.shared.models.animal;

import io.github.StardewValley.shared.models.map.Tile;

import java.util.*;

import io.github.StardewValley.shared.models.animal.Node;

public class AStarPathfinder {
    private List<Tile> tiles;

    public AStarPathfinder(List<Tile> tiles) {
        this.tiles = tiles;
    }

    public List<Tile> findPath(int startX, int startY, int endX, int endY) {
        Tile startTile = getTileAt(startX, startY);
        Tile endTile = getTileAt(endX, endY);

        // بررسی اولیه برای معتبر بودن تایل‌های شروع و پایان
        if (startTile == null || endTile == null || !startTile.isWalkAble() || !endTile.isWalkAble()) {
            return null; // مسیر نامعتبر است
        }

        // استفاده از HashMap برای دسترسی سریع به Node‌های ساخته‌شده برای هر تایل
        Map<Tile, Node> allNodes = new HashMap<>();

        Node startNode = new Node(startTile);
        Node endNode = new Node(endTile);

        allNodes.put(startTile, startNode);

        // openList یک صف اولویت است که همیشه ارزان‌ترین گره را در ابتدا نگه می‌دارد
        PriorityQueue<Node> openList = new PriorityQueue<>(Comparator.comparingDouble(Node::getF));

        // closedList مجموعه‌ای از گره‌هایی است که قبلاً بررسی شده‌اند
        Set<Node> closedList = new HashSet<>();

        startNode.g = 0;
        startNode.h = heuristic(startNode, endNode);

        openList.add(startNode);

        while (!openList.isEmpty()) {
            Node current = openList.poll(); // گره با کمترین هزینه (f) را بردار

            // اگر به مقصد رسیدیم، مسیر را بساز و برگردان
            if (current.tile.equals(endTile)) {
                return buildPath(current);
            }

            closedList.add(current);

            for (Tile neighborTile : getWalkableNeighbors(current.tile)) {
                // اگر همسایه قبلاً بررسی شده، از آن صرف نظر کن
                Node neighborNode = allNodes.getOrDefault(neighborTile, new Node(neighborTile));
                allNodes.put(neighborTile, neighborNode);

                if (closedList.contains(neighborNode)) {
                    continue;
                }

                // هزینه حرکت از گره فعلی تا همسایه (معمولاً 1 برای حرکت افقی/عمودی)
                float tentativeG = current.g + 1;

                // اگر مسیر جدید به این همسایه بهتر از مسیر قبلی است
                // یا اگر این همسایه هنوز در openList نیست
                if (tentativeG < neighborNode.g || !openList.contains(neighborNode)) {
                    neighborNode.parent = current;
                    neighborNode.g = tentativeG;
                    neighborNode.h = heuristic(neighborNode, endNode); // مقدار h برای هر گره ثابت است

                    // ✅ ***نکته کلیدی و اصلاح اصلی اینجاست***
                    // برای اینکه PriorityQueue موقعیت گره را بر اساس هزینه جدیدش به‌روز کند،
                    // باید آن را حذف کرده و دوباره اضافه کنیم.
                    if (openList.contains(neighborNode)) {
                        openList.remove(neighborNode);
                    }
                    openList.add(neighborNode);
                }
            }
        }

        return null; // اگر حلقه تمام شد و به مقصد نرسیدیم، یعنی مسیری وجود ندارد
    }

    /**
     * مسیر نهایی را با دنبال کردن parent ها از گره پایانی به گره ابتدایی بازسازی می‌کند.
     */
    private List<Tile> buildPath(Node endNode) {
        List<Tile> path = new ArrayList<>();
        Node current = endNode;
        while (current != null) {
            path.add(current.tile);
            current = current.parent;
        }
        Collections.reverse(path); // لیست را برعکس می‌کنیم تا از شروع به پایان باشد
        return path;
    }

    /**
     * هیوریستیک (تخمین هزینه) فاصله منهتن را محاسبه می‌کند.
     * برای نقشه‌های گریدی که حرکت قطری ندارند، بسیار مناسب است.
     */
    private float heuristic(Node a, Node b) {
        return Math.abs(a.tile.getX() - b.tile.getX()) + Math.abs(a.tile.getY() - b.tile.getY());
    }

    /**
     * یک تایل را بر اساس مختصات x و y پیدا می‌کند.
     * نکته: این متد به دلیل جستجوی خطی، کند است.
     */
    private Tile getTileAt(int x, int y) {
        for (Tile t : tiles) {
            if (t.getX() == x && t.getY() == y) {
                return t;
            }
        }
        return null;
    }

    /**
     * همسایه‌های قابل عبور (بالا، پایین، چپ، راست) یک تایل را برمی‌گرداند.
     */
    private List<Tile> getWalkableNeighbors(Tile tile) {
        List<Tile> neighbors = new ArrayList<>();
        int[] dx = {0, 0, 1, -1}; // راست، چپ
        int[] dy = {1, -1, 0, 0}; // بالا، پایین

        for (int i = 0; i < 4; i++) {
            int nx = tile.getX() + dx[i];
            int ny = tile.getY() + dy[i];
            Tile neighbor = getTileAt(nx, ny);
            if (neighbor != null && neighbor.isWalkAble()) {
                neighbors.add(neighbor);
            }
        }
        return neighbors;
    }
}
