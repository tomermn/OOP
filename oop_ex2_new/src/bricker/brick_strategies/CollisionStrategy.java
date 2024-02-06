package bricker.brick_strategies;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;

public interface CollisionStrategy {
    public void onCollision(GameObject obj1, GameObject obj2);
}
