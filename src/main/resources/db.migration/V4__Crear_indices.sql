-- Índice para búsqueda por título de tópico
CREATE FULLTEXT INDEX idx_topico_titulo ON topicos(titulo);

-- Índice para búsqueda por contenido de tópico
CREATE FULLTEXT INDEX idx_topico_contenido ON topicos(contenido);

-- Índice compuesto para búsquedas combinadas
CREATE FULLTEXT INDEX idx_topico_busqueda ON topicos(titulo, contenido);