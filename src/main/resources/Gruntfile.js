module.exports = function (grunt) {
    grunt.initConfig({
        pkg: grunt.file.readJSON('package.json'),
        meta: {
        },
        jshint: {
            files: [
                'Gruntfile.js',
                'assets/js/spa/*.js',
                'assets/js/custom.js',
                'assets/js/admin-handlebars.js'
            ],
            options: {
                globals: {
                    jQuery: true
                }
            }
        },
        sass: {
            dist: {
                files: {
                    'assets/css/custom.css': 'assets/css/custom.scss'
                }
            }
        },
        uglify: {
            options: {
                banner: '/*! <%= pkg.name %> - v<%= pkg.version %> - ' +
                        '<%= grunt.template.today("yyyy-mm-dd") %> */\n'
            },
            build: {
                src: [
                    'assets/js/custom.js'
                ],
                dest: 'assets/js/custom.min.js'
            }
        },
        copy: {
            customjs: {
                expand: true,
                cwd: 'assets/js',
                src: [
                    '**',
                ],
                dest: 'static/js'
            },
            customcss: {
                expand: true,
                cwd: 'assets/css',
                src: [
                    '**',
                ],
                dest: 'static/css'
            },
            customimg: {
                expand: true,
                cwd: 'assets/img',
                src: [
                    '**',
                ],
                dest: 'static/img'
            },
            js: {
                expand: true,
                flatten: true,
                src: [
                    'bower_components/angular/angular.js',
                    'bower_components/angular/angular.min.js',
                    'bower_components/angular-route/angular-route.js',
                    'bower_components/angular-route/angular-route.min.js',
                    'bower_components/jquery/dist/jquery.js',
                    'bower_components/jquery/dist/jquery.min.js',
                    'bower_components/jquery-validation/dist/jquery.validate.min.js',
                    'bower_components/bootstrap/dist/js/bootstrap.js',
                    'bower_components/bootstrap/dist/js/bootstrap.min.js',
                    'bower_components/handlebars/handlebars.js',
                    'bower_components/handlebars/handlebars.min.js'
                ],
                dest: 'static/js'
            },
            css: {
                expand: true,
                flatten: true,
                src: [
                    'bower_components/bootstrap/dist/css/*'
                ],
                dest: 'static/css'
            },
            fonts: {
                expand: true,
                flatten: true,
                src: [
                    'bower_components/bootstrap/dist/fonts/*'
                ],
                dest: 'static/fonts'
            }
        },
        watch: {
            files: ['<%= jshint.files %>'],
            tasks: ['jshint']
        }
    });

    grunt.loadNpmTasks('grunt-contrib-jshint');
    grunt.loadNpmTasks('grunt-contrib-sass');
    grunt.loadNpmTasks('grunt-contrib-uglify');
    grunt.loadNpmTasks('grunt-contrib-copy');
    grunt.loadNpmTasks('grunt-contrib-watch');

    grunt.registerTask('default', ['jshint', 'sass', 'uglify', 'copy']);
};