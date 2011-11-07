#!/usr/bin/env ruby
require 'rubygems'
require 'eventmachine'
require 'ArgsParser'

parser = ArgsParser.parser
parser.bind(:port, :p, 'port', 8082)
parser.bind(:help, :h, 'show help')

first, params = parser.parse(ARGV)
if parser.has_option(:help)
  puts parser.help
  puts "e.g.  ruby socket-server.rb --port 8082"
  exit 1
end

@@channel = EM::Channel.new

class EchoServer < EM::Connection
  def post_init
    @sid = @@channel.subscribe do |mes|
      send_data mes
    end
    puts "new client <#{@sid}>"
    @@channel.push "new client <#{@sid}> connected\n"
  end

  def receive_data data
    return if data.strip.size < 1
    puts "<#{@sid}> #{data}"
    send_data "echo to <#{@sid}> : #{data}\n"
  end

  def unbind
    puts "unbind <#{@sid}>"
    @@channel.unsubscribe @sid
  end
end

EM::run do
  EM::start_server('0.0.0.0', params[:port].to_i, EchoServer)
  puts "server start - port #{params[:port].to_i}"

  EM::add_periodic_timer(5) do
    puts msg = "this is broadcast message : #{Time.now.to_s}\n"
    @@channel.push msg
  end
end
