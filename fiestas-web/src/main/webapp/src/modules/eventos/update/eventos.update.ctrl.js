(function (ng) {
    var mod = ng.module("eventoModule");
    mod.constant("eventosContext", "api/eventos");
    mod.controller('eventoUpdateCtrl', ['$scope', '$http', 'eventosContext', '$state', 'booksContext', '$rootScope',
        /**
         * @ngdoc controller
         * @name eventos.controller:eventoUpdateCtrl
         * @description
         * Definición del controlador auxiliar para actualizar Eventos. 
         * @param {Object} $scope Referencia injectada al Scope definida para este
         * controlador, el scope es el objeto que contiene las variables o 
         * funciones que se definen en este controlador y que son utilizadas 
         * desde el HTML.
         * @param {Object} $http Objeto injectado para la manejar consultas HTTP
         * @param {Object} eventosContext Constante injectada que contiene la ruta
         * donde se encuentra el API de Eventos en el Backend.
         * @param {Object} $state Dependencia injectada en la que se recibe el 
         * estado actual de la navegación definida en el módulo.
         * @param {Object} $filter Dependencia injectada para hacer filtros sobre
         * arreglos.
         */
        function ($scope, $http, eventosContext, $state, $rootScope) {
            $rootScope.edit = true;

            $scope.data = {};

            $scope.selectedItems = [];

            $scope.availableItems = [];

            var idEvento = $state.params.eventoId;

            //Consulto el evento a editar.
            $http.get(eventosContext + '/' + idEvento).then(function (response) {
                var evento = response.data;
                $scope.data.celebrado = evento.celebrado;
                $scope.data.fecha = new Date(evento.fecha);
                $scope.data.descripcion = evento.descripcion;
                $scope.data.invitados = evento.invitados;
                $scope.data.lugar = evento.lugar;
                $scope.data.nombre = evento.nombre;
                $scope.data.pago = evento.pago;
                //$scope.getBooks(evento.books);
            });

            /**
             * @ngdoc function
             * @name getBooks
             * @methodOf eventos.controller:eventoUpdateCtrl
             * @description
             * Esta función recarga la información de los libros del evento.
             * @param {[Object]} books Los libros a actualizar del evento
             */
//            $scope.getBooks = function (books) {
//
//                $http.get(booksContext).then(function (response) {
//
//                    $scope.allBooks = response.data;
//                    $scope.booksEvento = books;
//
//                    var filteredBooks = $scope.allBooks.filter(function (book) {
//                        return $scope.booksEvento.filter(function (bookEvento) {
//                            return bookEvento.id === book.id;
//                        }).length === 0;
//                    });
//
//                    var unFilteredBooks = $scope.allBooks.filter(function (book) {
//                        return $scope.booksEvento.filter(function (bookEvento) {
//                            return bookEvento.id === book.id;
//                        }).length !== 0;
//                    });
//
//                    if ($scope.booksEvento.length === 0) {
//
//                        $scope.availableItems = $scope.allBooks;
//
//                    } else {
//
//                        $scope.selectedItems = unFilteredBooks;
//                        $scope.availableItems = filteredBooks;
//                    }
//
//
//                });
//            };

            /**
             * @ngdoc function
             * @name createEvento
             * @methodOf eventos.controller:eventoUpdateCtrl
             * @description
             * Crea un nuevo evento con los libros nuevos y la información del
             * $scope.
             */
            $scope.createEvento = function () {
                $http.put(eventosContext + "/" + idEvento, $scope.data).then(function (response) {
//                    if ($scope.selectedItems.length >= 0) {
//                        $http.put(eventosContext + "/" + response.data.id + "/books", $scope.selectedItems).then(function (response) {
//                        });
//                    }
                    //Evento created successfully
                    $state.go('eventosList', {eventoId: response.data.id}, {reload: true});
                });
            };
        }
    ]);
}
)(window.angular);


