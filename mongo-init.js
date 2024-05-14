print('Start #################################################################');

db = db.getSiblingDB('wishlist');

db.createCollection('product');

db.product.insertMany([
    {_id:"1"  , "name": "Notebook DELL" },
    {_id:"2"  , "name": "Notebook ACER" },
    {_id:"3"  , "name": "Desktop Positivo" },
    {_id:"4"  , "name": "XBOX Series S" },
    {_id:"5"  , "name": "XBOX Series X" },
    {_id:"6"  , "name": "PS5 Slim" },
    {_id:"7"  , "name": "Smartphone Xiaomi Redmi Note 13" },
    {_id:"8"  , "name": "Monitor AOC 27" },
    {_id:"9"  , "name": "Raspberry Pi 4B 8GB" },
    {_id:"10" , "name": "SSD kingston 940GB M2" },
    {_id:"11" , "name": "Ventilador Arno 27 4P" },
    {_id:"12" , "name": "Kit Lego AD27 Star Wars" },
    {_id:"13" , "name": "Livro Starfinder RPG Livro Básico" },
    {_id:"14" , "name": "Livro Starfinder RPG Mundos do Pacto" },
    {_id:"15" , "name": "Livro UML2 Uma Abordagem Prática" },
    {_id:"16" , "name": "Livro Código Limpo" },
    {_id:"17" , "name": "Kit IPTV Hardware 2.0" },
    {_id:"18" , "name": "Conjunto de Panelas Tramontina T24" },
    {_id:"19" , "name": "Fitas de Glicemia Accu-check Active 50U" },
    {_id:"20" , "name": "Tinta Geral TekBond Super Color Preto Fosco Metal" },
    {_id:"21" , "name": "Capacete NoRisk 58 Blade 3" },
    {_id:"22" , "name": "BoardGame 7Wonders 2ª Edição" }
]);

db.createCollection('client');

db.client.insertMany([
    {_id:"1", "name": "José Roberto dos Santos" },
    {_id:"2", "name": "Pedro Macklaren Silva" },
    {_id:"3", "name": "João das Neves" },
    {_id:"4", "name": "Bilbo Bolseiro Farias" }
]);

db.createCollection('wishlist');

db.wishlist.insertMany([
    {     _id:"1",
          "name": "Lista Cheia",
          "clientId": "1",
          "productIds": ["1","2","3","4","5","6","7","8","9","10",
                         "11","12","13","14","15","16","17","18","19","20"
          ]
        },
        {
        _id:"2",
        "name": "Lista Vazia",
        "clientId": "1",
        "productIds": []}
]);


print('END #################################################################');