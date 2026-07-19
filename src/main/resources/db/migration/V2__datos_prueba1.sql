-- INSERTAR LIBROS
INSERT INTO libro (
    autor,
    titulo,
    opinion,
    valoracion,
    genero,
    fecha_actualizacion,
    fecha_ingreso
)
VALUES
(
    'R. R. Tolkien',
    'El Señor de los Anillos: La Comunidad del Anillo',
    'Me gustó el libro. Recuerdo que la segunda vez que lo leí lo disfruté más. La primera vez no lo entendí mucho y creo que no lo terminé.
Tolkien es muy limpio con su escritura y tiene personajes muy caricaturescos con los que no dejo de sorprenderme. Es un excelente creador de mundos.',
    5,
    'LITERARIO',
    NULL,
    DATE '2024-10-10'
),
(
    'Kristin Hannah',
    'El Ruiseñor',
    'Ficción histórica que disfruté mucho leer. Me pareció que está muy bien escrita y me gustó como estaba escrita Isabelle, ya que era apasionada
e impulsiva, pero también tenía defectos y eso se hace ver a lo largo de la novela.',
    5,
    'LITERARIO',
    NULL,
    DATE '2025-07-15'
),
(
    'Clare Pooley',
    'Si dijeramos la verdad',
    'Me encanto leer este libro. Habla sobre la cotidianidad de la vida y los dramas que surgen en ella, de una manera muy linda y realista que
asocia a los diferentes personajes y los problemos con los que uno puede sentirse identificada, dinero, popularidad, soledad. Pienso leerlo de nuevo
para aprender más de su enseñanza y la forma en la que ve el mundo.',
    5,
    'LITERARIO',
    NULL,
    DATE '2024-08-11'
),
(
    'Markus Zuzak',
    'La Ladrona de Libros',
    'Había escuchado de ella cuando tenía más o menos 15 años de edad. Era el libro favorito de una escritora de fics que adoraba. Pero no la leí
hasta que se la pidieron a mi hermana a principios de año. Me gustó que todo estuviera relatado por la muerte, que a pesar de su trabajo, demostró
una empatía y sentido del humor deslumbrante. Me encantó Rudy, que representaba la inocencia, la amistad y la infancia de Liesel. Y sin duda
las relaciones que se entretejieron con los demás personajes.',
    5,
    'LITERARIO',
    NULL,
    DATE '2026-04-08'
);

-- INSERTAR ETIQUETAS
INSERT INTO etiqueta (descripcion, id_libro)
VALUES
('FANTASIA_EPICA', 1),
('FANTASIA', 1),
('NOVELA', 1),

('NOVELA_BELICA', 2),
('NOVELA', 2),
('DRAMA', 2),

('FEEL_GOOD', 3),
('AMISTAD', 3),
('NOVELA', 3),

('NOVELA', 4),
('NOVELA_BELICA', 4),
('ADULTO_JOVEN', 4);