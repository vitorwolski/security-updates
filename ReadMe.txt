
Prezados, seguem algumas instruções e considerações sobre o projeto:

>> O projeto do angular está na pasta "security-updates-angular" na própria pasta do projeto java

>> O projeto do java possui um arquivo de configurações em "./propriedades/configs.properties"

>> As configurações são as informações para a conexão do jdbc ao banco de dados, incluindo a url de conexão (jdbc), o usuário (user) e a senha (password) do banco a ser utilizado

>> Dentro da pasta "./script" há um script para a criação da tabela do banco, porém se o programa for executado, ele verifica se a tabela existe e, caso não exista, ele mesmo cria

>> O servidor http que o java levanta para atender as requests do angular está programado para a porta 64779, e no angular a solicitação está de forma fixa para "http://localhost:64779"

>> Como precisei aprender angular para esse projeto (antes só tinha feito um hello world básico), gastei um tempo nesse aprendizado, então o visual da tela do angular ficou um pouco mais simples