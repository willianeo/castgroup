# castgroup

Prezado avalidador, como pode ver, não consegui criar um README para documentar minha apliação.

Gostaria de pontuar algumas coisas que fui empurrando para o final e acabei não voltando e corrigindo.:
1) QRCode/E-Mail: Os destinatários ficaram fixos na aplicação.
    Assim, o QRCode não será enviado para o usuário que está cadastrando so funcionários, o que era esperado.
    Por gentileza, se poder alterar para o seu email, a fim de validar a funcionalidade.
    O QRCode é retornado no Response, e pode ser visualizado em modo debug cliente no seu navegador ou Postman.
    
2) Criei toda logica solicitada para o Backend.
    Acabei adicionando Endpoints a mais e também adicionei um sistema de autenticação mais sofisticado.
    Você pode testar essa funcionalidade. Na pagina "/signup", crie um usuário e adicionando perfil de "USR".
    O cadastro de funcionário emitira um alerta colocando a falta de permissão em forma de mensagem.
    Para cadastrar um funcionário, você precisa ter um perfil de "ADMIM ou "PM".
    
3) Banco de dados: Criei os perfis solicitados para o banco de dados. H2/MEM como banco de testes e H2/File como produção.

4) Testes: Na camada de testes, só o perfil criado para testes foi configurado.
    
4) FrontEnd: não consegui criar todas as telas. Criei uma tela de login, uma tela de Registro de usuário e a tela de registro de funcionários.
    Tratei varias excesssões.
    No cadastro de funcionário, a única regra que faltou no backend, a matricula do funcionário.
    Você não pode cadastrar duas matriculas iguais para funcionários diferentes, e nesse caso específico, precisa controlar manualmente.
    No cadastro de usuário, eu tratei o cadastro de e-mail que é Unique também.
    Não utilizei boas praticas no front. Tentei ser o mais rapido possível.
    O controle de sessão, no front-end é feito de forma simples, baseada penas nas mensagem de erro retornada do backend.
    Para armazenar o Token, utilizei Cookies.
    
 
