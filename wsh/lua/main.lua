require "collision"

function love.load()
    math.randomseed(os.time())
    player = {}
    player.x = 50
    player.y = 300
    player.w = 85
    player.h = 100
    player.direction = "down"

    coins = {}
    score = 0
    sounds = {}
    sounds.coin = love.audio.newSource("assets/sounds/coin.ogg", "static")

    fonts = {}
    fonts.large = love.graphics.newFont("assets/fonts/Gamer.ttf", 36)

    images = {}
    images.background = love.graphics.newImage("assets/images/ground.png")
    images.coin = love.graphics.newImage("assets/images/coin.png")
    images.up = love.graphics.newImage("assets/images/player/player_up.png")
    images.down = love.graphics.newImage("assets/images/player/player_down.png")
end
function love.update(dt)
    local i = 5
    if love.keyboard.isDown("right") then 
        player.x = player.x + i
    elseif love.keyboard.isDown("left") then 
        player.x = player.x - i
    elseif love.keyboard.isDown("up") then
        player.y = player.y - i
    elseif love.keyboard.isDown("down") then
        player.y = player.y + i
    elseif love.keyboard.isDown("space") then
        player.x = 50
        player.y = 300
    end
    for i=#coins, 1 , -1 do
        local coin = coins[i]
        if AABB(player.x, player.y, player.w, player.h, coin.x, coin.y, coin.w, coin.h) then
            sounds.coin:play()
            table.remove(coins, i)
            score = score + 1
        end
    end
    if math.random() < 0.01 then
        local coin = {}
        coin.w = 56
        coin.h = 56
        coin.x = math.random(0, 800 - coin.w)
        coin.y = math.random(0, 600 - coin.h)
        table.insert(coins, coin)
    end
end
function love.draw()
    for x =0, love.graphics.getWidth(), images.background:getWidth() do
        for y = 0, love.graphics.getHeight(), images.background:getHeight() do
            love.graphics.draw(images.background, x, y)
        end
    end
    love.graphics.rectangle("fill", player.x, player.y, player.w, player.h)
    for i=1, #coins, 1 do
        local coin = coins[i]
        love.graphics.draw(images.coin, coin.x, coin.y)
    end
    love.graphics.setFont(fonts.large)
    love.graphics.print("SCORE: " .. score, 10,10)
end
