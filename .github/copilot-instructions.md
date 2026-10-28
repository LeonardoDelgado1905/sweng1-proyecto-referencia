# Instrucciones para asistentes de IA

## Ambiente
- Usa siempre ./mvnw, nunca mvn: Maven no está instalado en este ambiente.
- Arranca la aplicación con ./mvnw spring-boot:run en una terminal normal,
  nunca como tarea ni en segundo plano.

## Commits
- Después de cada cambio, ejecuta ./mvnw test.
- Si todas las pruebas pasan, haz commit con un mensaje en español, en
  infinitivo, que describa el cambio (ej.: "Agregar vista de inicio").
- Si alguna prueba falla, no hagas commit: explica el fallo y detente.
- Nunca hagas push. El push lo hace una persona después de revisar.
- Un commit por cambio lógico; no mezcles cambios no relacionados.
