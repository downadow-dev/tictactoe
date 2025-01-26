/*
   Copyright 2025 downadow

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       https://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
*/

package downadow.tictactoe;

import com.badlogic.gdx.*;
import com.badlogic.gdx.net.*;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.graphics.glutils.*;
import com.badlogic.gdx.utils.*;
import com.badlogic.gdx.utils.viewport.*;
import com.badlogic.gdx.math.*;
import com.badlogic.gdx.Input.*;
import java.util.Random;

public class Main implements ApplicationListener {
    SpriteBatch batch;
    ShapeRenderer shape;
    FitViewport viewport;
    Vector2 touch;
    final int X = 1, O = 2;
    int next = X;
    int[][] map = {
        {0, 0, 0},
        {0, 0, 0},
        {0, 0, 0}
    };
    Texture xTexture, oTexture;
    
    public void create() {
        touch = new Vector2();
        batch = new SpriteBatch();
        shape = new ShapeRenderer();
        viewport = new FitViewport(5, 4);
        xTexture = new Texture("x.png");
        oTexture = new Texture("o.png");
        
        Gdx.input.setInputProcessor(new InputAdapter() {
            public boolean touchDown(int x, int y, int ptr, int btn) {
                touch.set(x, y);
                viewport.unproject(touch);
                
                if(next == 0) {
                    for(int i = 0; i < 3; i++) {
                        for(int j = 0; j < 3; j++) {
                            map[i][j] = 0;
                        }
                    }
                    next = X;
                    return true;
                }
                
                /* ход бота */
                else if(touch.x > 0f && touch.x < 0.6f && touch.y > 0f && touch.y < 0.6f) {
                    generateNext(next == X ? O : X, next);
                    next = (next == X ? O : X);
                    check();
                    return true;
                }
                /* ввод */
                else if(touch.x > 1f && touch.x < 2f && touch.y > 2.5f && touch.y < 3.5f && map[0][0] == 0) {
                    map[0][0] = next;
                    next = (next == X ? O : X);
                    check();
                    return true;
                } else if(touch.x > 2f && touch.x < 3f && touch.y > 2.5f && touch.y < 3.5f && map[0][1] == 0) {
                    map[0][1] = next;
                    next = (next == X ? O : X);
                    check();
                    return true;
                } else if(touch.x > 3f && touch.x < 4f && touch.y > 2.5f && touch.y < 3.5f && map[0][2] == 0) {
                    map[0][2] = next;
                    next = (next == X ? O : X);
                    check();
                    return true;
                } else if(touch.x > 1f && touch.x < 2f && touch.y > 1.5f && touch.y < 2.5f && map[1][0] == 0) {
                    map[1][0] = next;
                    next = (next == X ? O : X);
                    check();
                    return true;
                } else if(touch.x > 2f && touch.x < 3f && touch.y > 1.5f && touch.y < 2.5f && map[1][1] == 0) {
                    map[1][1] = next;
                    next = (next == X ? O : X);
                    check();
                    return true;
                } else if(touch.x > 3f && touch.x < 4f && touch.y > 1.5f && touch.y < 2.5f && map[1][2] == 0) {
                    map[1][2] = next;
                    next = (next == X ? O : X);
                    check();
                    return true;
                } else if(touch.x > 1f && touch.x < 2f && touch.y > 0.5f && touch.y < 1.5f && map[2][0] == 0) {
                    map[2][0] = next;
                    next = (next == X ? O : X);
                    check();
                    return true;
                } else if(touch.x > 2f && touch.x < 3f && touch.y > 0.5f && touch.y < 1.5f && map[2][1] == 0) {
                    map[2][1] = next;
                    next = (next == X ? O : X);
                    check();
                    return true;
                } else if(touch.x > 3f && touch.x < 4f && touch.y > 0.5f && touch.y < 1.5f && map[2][2] == 0) {
                    map[2][2] = next;
                    next = (next == X ? O : X);
                    check();
                    return true;
                }
                
                return false;
            }
        });
        
    }
    
    public void render() {
        ScreenUtils.clear(Color.WHITE);
        viewport.apply();
        viewport.getCamera().update();
        
        shape.setProjectionMatrix(viewport.getCamera().combined);
        shape.begin(ShapeRenderer.ShapeType.Line);
        shape.setColor(0.4f, 0.4f, 0.4f, 1);
        shape.line(1f, 1.5f, 4f, 1.5f);
        shape.line(1f, 2.5f, 4f, 2.5f);
        shape.line(2f, 0.5f, 2f, 3.5f);
        shape.line(3f, 0.5f, 3f, 3.5f);
        shape.end();
        
        batch.setProjectionMatrix(viewport.getCamera().combined);
        batch.begin();
        
        if(next == X)
            batch.draw(xTexture, 0, 0, 0.6f, 0.6f);
        else if(next == O)
            batch.draw(oTexture, 0, 0, 0.6f, 0.6f);
        
        for(int i = 0; i < 3; i++) {
            for(int j = 0; j < 3; j++) {
                if(map[i][j] == X)
                    batch.draw(xTexture, 1 + j, 4 - (0.5f + i) - 1, 1, 1);
                else if(map[i][j] == O)
                    batch.draw(oTexture, 1 + j, 4 - (0.5f + i) - 1, 1, 1);
            }
        }
        batch.end();
    }
    
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }
    
    public void pause()   {}
    public void resume()  {}
    public void dispose() {}
    
    
    /* проверка */
    private void check() {
        boolean ok = false;
        loop:
        for(int i = 0; i < 3; i++) {
            for(int j = 0; j < 3; j++) {
                if(map[i][j] == 0) {
                    ok = true;
                    break loop;
                }
            }
        }
        if(!ok) next = 0;
        
        if(map[0][0] == X && map[0][1] == X && map[0][2] == X)
            next = 0;
        else if(map[1][0] == X && map[1][1] == X && map[1][2] == X)
            next = 0;
        else if(map[2][0] == X && map[2][1] == X && map[2][2] == X)
            next = 0;
        else if(map[0][0] == X && map[1][0] == X && map[2][0] == X)
            next = 0;
        else if(map[0][1] == X && map[1][1] == X && map[2][1] == X)
            next = 0;
        else if(map[0][2] == X && map[1][2] == X && map[2][2] == X)
            next = 0;
        else if(map[0][0] == X && map[1][1] == X && map[2][2] == X)
            next = 0;
        else if(map[0][2] == X && map[1][1] == X && map[2][0] == X)
            next = 0;
        
        else if(map[0][0] == O && map[0][1] == O && map[0][2] == O)
            next = 0;
        else if(map[1][0] == O && map[1][1] == O && map[1][2] == O)
            next = 0;
        else if(map[2][0] == O && map[2][1] == O && map[2][2] == O)
            next = 0;
        else if(map[0][0] == O && map[1][0] == O && map[2][0] == O)
            next = 0;
        else if(map[0][1] == O && map[1][1] == O && map[2][1] == O)
            next = 0;
        else if(map[0][2] == O && map[1][2] == O && map[2][2] == O)
            next = 0;
        else if(map[0][0] == O && map[1][1] == O && map[2][2] == O)
            next = 0;
        else if(map[0][2] == O && map[1][1] == O && map[2][0] == O)
            next = 0;
    }
    
    /* ход бота */
    private void generateNext(int player, int bot) {
        /* побеждаем */
        
        if(map[0][0] == bot && map[1][1] == bot && map[2][2] == 0) {
            map[2][2] = bot;
            return;
        } else if(map[0][0] == 0 && map[1][1] == bot && map[2][2] == bot) {
            map[0][0] = bot;
            return;
        } else if(map[0][0] == bot && map[1][1] == 0 && map[2][2] == bot) {
            map[1][1] = bot;
            return;
        } else if(map[0][2] == bot && map[1][1] == bot && map[2][0] == 0) {
            map[2][0] = bot;
            return;
        } else if(map[0][2] == 0 && map[1][1] == bot && map[2][0] == bot) {
            map[0][2] = bot;
            return;
        } else if(map[0][2] == bot && map[1][1] == 0 && map[2][0] == bot) {
            map[1][1] = bot;
            return;
        }
        
        for(int i = 0; i < 3; i++) {
            if(map[i][0] == bot && map[i][1] == bot && map[i][2] == 0) {
                map[i][2] = bot;
                return;
            } else if(map[i][0] == 0 && map[i][1] == bot && map[i][2] == bot) {
                map[i][0] = bot;
                return;
            } else if(map[i][0] == bot && map[i][1] == 0 && map[i][2] == bot) {
                map[i][1] = bot;
                return;
            } else if(map[0][i] == bot && map[1][i] == bot && map[2][i] == 0) {
                map[2][i] = bot;
                return;
            } else if(map[0][i] == 0 && map[1][i] == bot && map[2][i] == bot) {
                map[0][i] = bot;
                return;
            } else if(map[0][i] == bot && map[1][i] == 0 && map[2][i] == bot) {
                map[1][i] = bot;
                return;
            }
        }
        
        /* защита */
        if(map[0][0] == player && map[1][1] == player && map[2][2] == 0) {
            map[2][2] = bot;
            return;
        } else if(map[0][0] == 0 && map[1][1] == player && map[2][2] == player) {
            map[0][0] = bot;
            return;
        } else if(map[0][0] == player && map[1][1] == 0 && map[2][2] == player) {
            map[1][1] = bot;
            return;
        } else if(map[0][2] == player && map[1][1] == player && map[2][0] == 0) {
            map[2][0] = bot;
            return;
        } else if(map[0][2] == 0 && map[1][1] == player && map[2][0] == player) {
            map[0][2] = bot;
            return;
        } else if(map[0][2] == player && map[1][1] == 0 && map[2][0] == player) {
            map[1][1] = bot;
            return;
        }
        
        for(int i = 0; i < 3; i++) {
            if(map[i][0] == player && map[i][1] == player && map[i][2] == 0) {
                map[i][2] = bot;
                return;
            } else if(map[i][0] == 0 && map[i][1] == player && map[i][2] == player) {
                map[i][0] = bot;
                return;
            } else if(map[i][0] == player && map[i][1] == 0 && map[i][2] == player) {
                map[i][1] = bot;
                return;
            } else if(map[0][i] == player && map[1][i] == player && map[2][i] == 0) {
                map[2][i] = bot;
                return;
            } else if(map[0][i] == 0 && map[1][i] == player && map[2][i] == player) {
                map[0][i] = bot;
                return;
            } else if(map[0][i] == player && map[1][i] == 0 && map[2][i] == player) {
                map[1][i] = bot;
                return;
            }
        }
        
        /* новый ход */
        
        int x = new Random().nextInt(3);
        int y = new Random().nextInt(3);
        if(map[y][x] != 0) {
            generateNext(player, bot);
            return;
        }
        
        map[y][x] = bot;
    }
}
